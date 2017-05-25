
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 *
 * @author Abdullah Hussein
 */
public class ImageThief {
    public static void main(String[] args) throws MalformedURLException, IOException {
        
        BufferedReader rd = new BufferedReader(new InputStreamReader(new URL( "صفحت الموقع").openStream()));
        ArrayList<String> ListOfImagesURL = new ArrayList<>();
        String s = rd.readLine();
        int end = 0;
        // الملف اللي تبي تخزنه له أنا سميته 
        // test.txt
        PrintWriter br = new PrintWriter(new File("test.txt"));
        while(s != null){
            try{
        for(int img = s.indexOf("<img") + "<img".length(); img > -1; s = s.substring(img + end)){
            try{
            int src = s.indexOf("src", img + 4);
             end = s.indexOf(".jpg" , src) ;
            
            String url = s.substring(src+ "src".length()+2 , end + ".jpg".length() );
            if(url.startsWith("http")) {
            System.out.println(url);
            br.println(url);
            }
            }catch(Exception e){
                break;
            }
        }
        }catch(Exception e){}
        s = rd.readLine();
        
        }
        br.close();
        rd.close();
        
       
    }
    
