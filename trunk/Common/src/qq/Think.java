package qq;
import java.io.BufferedReader;   
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;   
import java.io.InputStreamReader;   
import java.io.OutputStreamWriter;
import java.util.HashMap;   
import java.util.Map;   
import java.util.Set; 
    public class Think
    {
        public static Map<String, String> dic = new HashMap<String, String>();
        public static String p="c:/th.txt";
        public Think()
        {
        }
        public static String getWords(String msg)
        {
            msg = msg.replaceAll(" ", "");
            if (msg.indexOf("teach:")!=-1)
            {
                try
                {
                	String[] str = msg.split(":");
                    dic.put(str[1], str[2]);
                    saveFile(p);
                    return "лл�������֪ʶ���һ��μ����ĵģ��ٺ�";
                }
                catch(Exception e)
                {
                    return "����ҵĸ�ʽ����Ŷ���ҿ�������>_<���ҿ��Ķ��ĸ�ʽ��������:\nteach:����:��";
                }
            }
            String words = "";
            int mmax = -1;
            int mindex = -1;
            Set<String> dk = dic.keySet();
            for (int i = 0; i < dic.size(); i++)
            {
                int max = 0;

                for (int j = 0; j < msg.length(); j++)
                {
                    if (-1 != dk.toArray()[i].toString().indexOf(msg.charAt(j)))
                    {
                        max++;
                    }
                }
                if (max > mmax)
                {
                    mmax = max;
                    mindex = i;
                }
            }
            if(dic.containsKey(msg))
            {
            	words=dic.get(msg).toString();
            	return words;
            }
            if (mmax < msg.length()/2+1||mindex==-1)
            {
                words = "�Ҳ�֪����ô�ش�����Һ���\nʹ�����¸�ʽ����:\nteach:����:��\nлл�����֪ʶ���ٺ�";
            }
            else
            {
                words = dic.values().toArray()[mindex].toString();
            }
            return words;
        }
        public static void loadFile(String path)
        {
        	BufferedReader br=null;
			try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            
        	String ls = null;
            try {
            	String str="";
				while ((ls = br.readLine()) != null)
				{
					str+=ls;
				}
				String[] ss = str.split("&");
				for(int i=0;i<ss.length;i++)
				{
						String[] lps = ss[i].split("#");
						if(lps.length==2)
						{
							dic.put(lps[0], lps[1]);
						}
				}
				
				br.close();
			} catch (IOException e) {
				
			}
			
            
        }
        public static void saveFile(String path)
        {
        	BufferedWriter bw=null;
			try {
				bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path)));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            Object[] dk = dic.keySet().toArray();
            Object[] dv = dic.values().toArray();
            for (int i = 0; i < dic.size(); i++)
            {
                try {
					bw.write(dk[i] + "#" + dv[i]+"&");
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            try {
				bw.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            try {
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }


