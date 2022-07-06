package dark.composer.carpet.utils;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

//https://www.vogella.com/tutorials/AndroidServices/article.html
//music player//https://www.raywenderlich.com/20123726-android-services-getting-started
//https://www.geeksforgeeks.org/services-in-android-with-example/
//https://www.youtube.com/watch?v=bA7v1Ubjlzw
//https://github.com/pavan5208/android_sample_foreground_service
//https://github.com/jeetdholakia/ServicesAndBackgroundTasks
public class DownloadService extends IntentService {

    public static final String URL = "urlpath";
    public static final String FILENAME = "filename";
    public static final String FILEPATH = "filepath";
    public static final String RESULT = "result";
    public static final String PROGRESS = "progress";
    public static final String NOTIFICATION = "uz.micro.star.service1";
    private static final String TAG = "LOADER";
    private int result = -1;

    public DownloadService() {
        super("DownloadService");
    }

    // will be called asynchronously by Android
    @Override
    protected void onHandleIntent(Intent intent) {
        String urlPath = intent.getStringExtra(URL);
        String fileName = intent.getStringExtra(FILENAME);
        String filePath = "";
        int count;
        try {
            URL url = new URL(urlPath);
            URLConnection connection = url.openConnection();
            connection.connect();
            // getting file length
            int lengthOfFile = connection.getContentLength();
            // input stream to read file - with 8k buffer
            InputStream input = new BufferedInputStream(url.openStream(), 8192);
            //Create androiddeft folder if it does not exist
            File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/musics");
            if (!directory.exists()) {
                directory.mkdir();
            }

            // Output stream to write file
            File file = new File(directory.getAbsolutePath(), fileName + ".mp3");
            filePath = file.getAbsolutePath();
            OutputStream output = new FileOutputStream(file);

            byte[] data = new byte[1024];

            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress....
                // After this onProgressUpdate will be called
                publishProgress((int) ((total * 100) / lengthOfFile), 0);
                Log.d(TAG, "Progress: " + (int) ((total * 100) / lengthOfFile));

                // writing data to file
                output.write(data, 0, count);
            }

            // flushing output
            output.flush();

            // closing streams
            output.close();
            input.close();

            result = 1;

        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }

        publishResults(filePath, result);
    }

    private void publishResults(String outputPath, int result) {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(FILEPATH, outputPath);
        intent.putExtra(RESULT, result);
        sendBroadcast(intent);
    }

    private void publishProgress(int progress, int result) {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(PROGRESS, progress);
        intent.putExtra(RESULT, result);
        sendBroadcast(intent);
    }
}
