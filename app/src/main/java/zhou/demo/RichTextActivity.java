package zhou.demo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.callback.OnImageClickListener;
import com.zzhoujay.richtext.callback.OnUrlClickListener;

import org.xml.sax.Attributes;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class RichTextActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rich_text);

        RichText.initCacheDir(this);
        RichText.debugMode = true;

        final TextView textView = findViewById(R.id.text);

        textView.setHighlightColor(Color.TRANSPARENT);

        RichText.fromHtml(readAssets(this,"richtext.txt"))
                .autoPlay(true)
                .imageClick(new OnImageClickListener() {
                    @Override
                    public void imageClicked(List<String> imageUrls, int position, Attributes attributes,RichText richText) {
                        if (attributes!=null) {
                            System.out.println("title===="+attributes.getValue("title")+"alt===="+attributes.getValue("alt"));
                        }
                        System.out.println(imageUrls.get(position)+"--------"+position);
                    }
                })
                .urlClick(new OnUrlClickListener() {
                    @Override
                    public boolean urlClicked(String url) {
                        System.out.println(url);
                        return true;
                    }
                })
                .into(textView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "RecyclerView");
        menu.add(0, 1, 1, "ListView");
        menu.add(0, 2, 2, "Gif");
        menu.add(0, 3, 3, "Test");
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RichText.recycle();
    }

    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    public static String readAssets(Context context, String fileName){
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String text = new String(buffer, "utf-8");
            return text;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "读取错误，请检查文件名";
    }
}
