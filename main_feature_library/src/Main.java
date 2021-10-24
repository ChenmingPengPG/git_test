import java.io.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("hello world！ my module");
        Main m = new Main();
        m.start();
    }

    public static File findGitDir(File file) {
        File gitFile = null;
        while(file.getParentFile() != null) {
            file = file.getParentFile();
            if( file != null && file.isDirectory()) {
                File[] subFiles = file.listFiles(new FileFilter() {
                    @Override
                    public boolean accept(File file) {
                        return ".git".equals(file.getName());
                    }
                });
                if (subFiles != null && subFiles.length > 0) {
                    gitFile = subFiles[0];
                    break;
                }
            }
        }
        return gitFile == null ? null : gitFile.getParentFile();
    }

    public void start(){
        File curFile = new File(getClass().getResource("/").getPath());
        File gitDir = findGitDir(curFile);
        Process process = null;
        String[] cmd = new String[]{"D:\\Program Files\\Git\\cmd\\git.exe", "blame", curFile.getPath()};
        try {
            process = Runtime.getRuntime().exec(cmd, null, gitDir);
            process.waitFor();
            InputStream fis= process.getInputStream();
            //用一个读输出流类去读
            InputStreamReader isr=new InputStreamReader(fis);
            //用缓冲器读行
            BufferedReader br=new BufferedReader(isr);
            String line=null;
            StringBuilder sb = new StringBuilder();
            //直到读完为止
            while((line=br.readLine())!=null)
            {
                System.out.println(line);
                sb.append(line).append("\n");
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
