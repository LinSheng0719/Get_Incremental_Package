/**
 * @Title: HelloSWT.java 
 * @Package code_builder.ui 
 * @Description:
 * @author linsheng 
 * @email hi.linsheng@foxmail.com   
 * @date 2015年12月22日 上午8:57:37 
 * @version V1.0   
 */
package get_incremental_package.ui;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.ls.service.main.CreateService;
import com.ls.service.model.CreateInfo;
import com.ls.service.utils.ClassUtils;
import com.ls.service.utils.DateUtils;
import com.ls.service.utils.FileUtils;
import com.ls.service.utils.PropertyUtils;
import com.ls.service.utils.StringUtils;

/**
 * @Title: HelloSWT.java
 * @Package code_builder.ui
 * @Description:
 * @author linsheng
 * @email hi.linsheng@foxmail.com
 * @date 2015年12月22日 上午8:57:37
 * @version V1.0
 */
public class GetPack extends Shell {

    /**
     * 基础信息
     */
    private CreateInfo info;

    private Text modify_datetime;
    private Text project_path;
    private Text project_webRoot;
    private Text project_out_path;
    private Label warmming_lable;

    public GetPack() {

        Display display = Display.getDefault();
        Shell shlCodebuilder = new Shell(display);
        shlCodebuilder.setText("生成增量包:");
        shlCodebuilder.setSize(476, 625);

        createView(shlCodebuilder);

        shlCodebuilder.open();
        shlCodebuilder.layout();
        while (!shlCodebuilder.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }

    }

    public void createView(Shell shlCodebuilder) {
        Label lblyyyymmddHhmmss = new Label(shlCodebuilder, SWT.NONE);
        lblyyyymmddHhmmss.setBounds(27, 59, 236, 17);
        lblyyyymmddHhmmss.setText("修改时间（yyyyMMdd HH:mm）:");

        Label label_1 = new Label(shlCodebuilder, SWT.NONE);
        label_1.setBounds(27, 144, 94, 17);
        label_1.setText("项目路径:");

        Label lblWeb = new Label(shlCodebuilder, SWT.NONE);
        lblWeb.setBounds(27, 226, 118, 17);
        lblWeb.setText("web应用文件夹:");

        Label label_3 = new Label(shlCodebuilder, SWT.NONE);
        label_3.setBounds(27, 307, 94, 17);
        label_3.setText("增量包存放路径:");

        modify_datetime = new Text(shlCodebuilder, SWT.BORDER);
        modify_datetime.setBounds(27, 95, 400, 23);

        project_path = new Text(shlCodebuilder, SWT.BORDER);
        project_path.setBounds(27, 179, 400, 23);

        project_webRoot = new Text(shlCodebuilder, SWT.BORDER);
        project_webRoot.setBounds(27, 264, 400, 23);

        project_out_path = new Text(shlCodebuilder, SWT.BORDER);
        project_out_path.setBounds(27, 343, 400, 23);

        Button btnNewButton = new Button(shlCodebuilder, SWT.NONE);
        btnNewButton.setBounds(27, 508, 400, 47);
        btnNewButton.setText("生成文件");

        warmming_lable = new Label(shlCodebuilder, SWT.NONE);
        warmming_lable.setBounds(27, 473, 400, 17);

        try {
            getConfigure();
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }

        btnNewButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (!checkInfo())
                    return;

                warmming_lable.setText("增量包生成中,请稍候...");
                CreateService cs = new CreateService();
                try {
                    cs.getFile(info);
                } catch (IOException e1) {
                    e1.printStackTrace();
                    warmming_lable.setText("生成失败");
                    return;
                }
                try {
                    setConfigure();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                warmming_lable.setText("生成成功");
            }
        });
    }

    @Override
    protected void checkSubclass() {
    }

    private boolean checkInfo() {
        Date mdate = null;
        try {
            String formart = "yyyyMMdd";
            if (modify_datetime.getText().length() > 9)
                formart = "yyyyMMdd hh:mm";
            mdate = DateUtils.parse(modify_datetime.getText(), formart);
        } catch (ParseException e) {
            warmming_lable.setText("时间格式有误");
            return false;
        }
        File projectFile = new File(project_path.getText());
        if (!projectFile.exists() || !projectFile.isDirectory()) {
            warmming_lable.setText("项目目录文件不存在");
            return false;
        }
        project_path.setText(projectFile.getAbsolutePath());
        File rootFile = new File(project_webRoot.getText());
        if (!rootFile.exists() || !rootFile.isDirectory()) {
            warmming_lable.setText("web路径目录不存在");
            return false;
        }
        project_webRoot.setText(rootFile.getAbsolutePath());
        
        File outFile = new File(project_out_path.getText());
        if (!outFile.exists()) {
            outFile.mkdirs();
        }
        project_out_path.setText(outFile.getAbsolutePath());

        info = new CreateInfo(mdate, projectFile, rootFile, outFile);

        return true;
    }

    private static final String CONFIG_FILE = ClassUtils.getProjectPath() + File.separator + "Get_Incremental_Package.properties";
    /**
     * @Title:setConfigure
     * @Description:保存配置信息
     * @param @throws ClassNotFoundException
     * @param @throws IOException
     * @return void
     */
    private void setConfigure() throws ClassNotFoundException, IOException {
        FileUtils.createFile(new File(CONFIG_FILE));
        PropertyUtils propertyUtils = new PropertyUtils(CONFIG_FILE);
        Properties properties = new Properties();
        properties.put("modify_datetime", modify_datetime.getText());
        properties.put("project_path", project_path.getText());
        properties.put("project_webRoot", project_webRoot.getText());
        properties.put("project_out_path", project_out_path.getText());
        propertyUtils.writeProperties(properties, DateUtils.getNow());
    }

    /**
     * @Title:getConfigure
     * @Description:配置上一次配置信息
     * @param @throws ClassNotFoundException
     * @param @throws IOException
     * @return void
     */
    private void getConfigure() throws ClassNotFoundException, IOException {
        FileUtils.createFile(new File(CONFIG_FILE));
        PropertyUtils propertyUtils = new PropertyUtils(CONFIG_FILE);
        if (StringUtils.notBlank(propertyUtils.getValue("modify_datetime")))
            modify_datetime.setText(propertyUtils.getValue("modify_datetime"));
        if (StringUtils.notBlank(propertyUtils.getValue("project_path")))
            project_path.setText(propertyUtils.getValue("project_path"));
        if (StringUtils.notBlank(propertyUtils.getValue("project_webRoot")))
            project_webRoot.setText(propertyUtils.getValue("project_webRoot"));
        if (StringUtils.notBlank(propertyUtils.getValue("project_out_path")))
            project_out_path.setText(propertyUtils.getValue("project_out_path"));
    }

    public static void main(String[] args) {
        new GetPack();
    }
}