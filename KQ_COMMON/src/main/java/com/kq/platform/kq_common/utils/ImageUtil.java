package com.kq.platform.kq_common.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * 相关图片操作的工具类
 */
public class ImageUtil {

    public static final int SCALE_IMAGE_WIDTH = 640;
    public static final int SCALE_IMAGE_HEIGHT = 960;

    /**
     * 根据图片路径来转图片成bitmap。
     * @param imagePath
     * @return
     */
    public static Bitmap decodeImage(String imagePath) {
        if (imagePath == null || imagePath.trim().length() == 0)
            return null;
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeFile(imagePath);
        }catch (Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 综合压缩方法，生成新的压缩图片文件
     * @param filePath
     * @param newFilePath
     */
    public static void finalCompressImageFile(String filePath, String newFilePath, boolean isLandscape){
        newFilePath = TextUtils.isEmpty(newFilePath)?filePath:newFilePath;
        Bitmap bitmap = decodeScaleImage(filePath,1280,720,isLandscape);
        try {
            compressImage(bitmap,newFilePath,200);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 根据图片路径来转图片成bitmap。
     * @param imagePath
     * @param width
     * @param height
     * @return
     */
    public static Bitmap decodeScaleImage(String imagePath, int width, int height, boolean isLandscape) {
        BitmapFactory.Options options = getBitmapOptions(imagePath);
        int inSampleSize = calculateInSampleSize(options, width, height);
        Log.d("img", "original wid" + options.outWidth + " original height:" + options.outHeight + " sample:" + inSampleSize);
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        Bitmap originBitmap = BitmapFactory.decodeFile(imagePath, options);
        int degree = readPictureDegree(imagePath);
        Bitmap bitmap = null;
        if(originBitmap != null && degree != 0) {
            if(originBitmap.getWidth() > originBitmap.getHeight()){
                bitmap = originBitmap;
                return bitmap;
            }
            bitmap = rotaingImageView(degree, originBitmap);
            originBitmap.recycle();
            originBitmap = null;
            return bitmap;
        } else if(isLandscape){
            if(originBitmap.getWidth() > originBitmap.getHeight()){
                bitmap = rotaingImageView(90, originBitmap);
                originBitmap.recycle();
                originBitmap = null;
            }else{
                bitmap = rotaingImageView(270, originBitmap);
                originBitmap.recycle();
                originBitmap = null;
            }
            return bitmap;
        }else {
            return originBitmap;
        }
    }
    /**
     * 根据图片路径来转图片成bitmap。
     * @param imagePath
     * @param width
     * @param height
     * @return
     */
    public static Bitmap decodeScaleImage(String imagePath, int width, int height) {
        BitmapFactory.Options options = getBitmapOptions(imagePath);
        int inSampleSize = calculateInSampleSize(options, width, height);
        Log.d("img", "original wid" + options.outWidth + " original height:" + options.outHeight + " sample:" + inSampleSize);
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        Bitmap originBitmap = BitmapFactory.decodeFile(imagePath, options);
        int degree = readPictureDegree(imagePath);
        Bitmap bitmap = null;
        if(originBitmap != null && degree != 0) {
            bitmap = rotaingImageView(degree, originBitmap);
            originBitmap.recycle();
            originBitmap = null;
            return bitmap;
        } else {
            return originBitmap;
        }
    }

    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static void compressImage(Bitmap image, String outPath, int maxSize)  throws IOException {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // scale
        int options = 100;
        // Store the bitmap into output stream(no compress)
        image.compress(Bitmap.CompressFormat.JPEG, options, os);
        // Compress by loop
        while ( os.toByteArray().length / 1024 > maxSize) {
            // Clean up os
            os.reset();
            // interval 10
            options -= 10;
            image.compress(Bitmap.CompressFormat.JPEG, options, os);
        }

        // Generate compressed image file
        FileOutputStream fos = new FileOutputStream(outPath);
        fos.write(os.toByteArray());
        fos.flush();
        fos.close();
    }


    /**
     * 综合压缩方法，生成新的压缩图片文件
     * @param filePath
     * @param newFilePath
     */
    public static void finalCompressImageFile(String filePath, String newFilePath){
        newFilePath = TextUtils.isEmpty(newFilePath)?filePath:newFilePath;
        Bitmap bitmap = decodeScaleImage(filePath,1280,720);
        try {
            compressImage(bitmap,newFilePath,200);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Bitmap设置
     * @param imagePath
     * @return
     */
    public static BitmapFactory.Options getBitmapOptions(String imagePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);
        return options;
    }

    /**
     * 计算最适应inSampleSize
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int width = options.outWidth;
        final int height = options.outHeight;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            //计算图片高度和我们需要高度的最接近比例值
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            //宽度比例值
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            //取比例值中的较大值作为inSampleSize
            inSampleSize = heightRatio > widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }


    /**
     * 读取图片旋转角度
     * @param imagePath
     * @return
     */
    public static int readPictureDegree(String imagePath) {
        int degree  = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(imagePath);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转图片
     * @param angle
     * @param bitmap
     * @return
     */
    public static Bitmap rotaingImageView(int angle , Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        System.out.println("angle2=" + angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    /**
     * Drawable 转成Bitmap
     *
     * @param drawable 被转的Drawable
     * @return bitmap
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    /**
     * Bitmap转换成byte数组
     */
    public static byte[] imgPath2Bytes(String fileName) {
        if(fileName != null && fileName.length()>0){
            File file = new File(fileName);
            byte[] bytes = null;

            InputStream is;
            try {
                is = new FileInputStream(file);

                bytes = new byte[is.available()];

                is.read(bytes);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bytes;
        }
        return  null;
    }

    public static byte[] bitmap2Bytes(Bitmap bm, int quality) {
           ByteArrayOutputStream baos = new ByteArrayOutputStream();
           bm.compress(Bitmap.CompressFormat.PNG, quality, baos);
           return baos.toByteArray();
     }

    /**
     * 文件路径转为base64编码
     * @return
     */
    public static String file2base64(String filepath) {
        if (filepath == null || filepath.trim().length() == 0)
            return "";
        try {
            Bitmap bmp = BitmapFactory.decodeFile(filepath);
            byte[] bts = bitmap2Bytes(bmp, 100);
            return new String(Base64Coder.encode(bts));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 有些控件UI设计师没有给点击效果，这个和是否敬业有关，但是作为Awesome developer.
     * 对自己的产品还是要有爱。他们不做，我们做。
     * <p/>
     * 仿Android4.0以上的Holo主题，点击之后通过Alpha值的改变来 区分点击效果。
     * <br /><b>API17以下的，LayoutButtonDrawable必须只能接受一个BitmapDrawable</b>
     */
    public static final class LayerButtonDrawable extends LayerDrawable {

        //  这个颜色提供给点击之后的的状态
        private LightingColorFilter pressedFilter = new LightingColorFilter(Color.LTGRAY, 1);

        // Alpha值 当这个控件被disable
        private int disabledAlpha = 100;
        // Alpha值 enable
        private int fullAlpha = 255;

        public LayerButtonDrawable(Drawable d) {
            super(new Drawable[]{d});
        }

        @Override
        public boolean isStateful() {
            return true;
        }

        @Override
        protected boolean onStateChange(int[] states) {
            boolean enabled = false;
            boolean pressed = false;

            for (int state : states) {
                if (state == android.R.attr.state_enabled)
                    enabled = true;
                else if (state == android.R.attr.state_pressed)
                    pressed = true;
            }
            mutate();
            if (enabled && pressed) { // 当正常状态点击
                setColorFilter(pressedFilter);
            } else if (!enabled) {
                setColorFilter(null);
                setAlpha(disabledAlpha);
            } else {
                setColorFilter(null);
                setAlpha(fullAlpha);
            }
            invalidateSelf();
            return super.onStateChange(states);
        }

        @Override
        public Drawable mutate() {
            return super.mutate();
        }
    }

    /**
     * UI设计的界面有各种圆角，有的上面有下面木有，有的下面有上面没有。照老办法drawable目录中会多出很多xml。
     * developer自有妙计
     */
    public static final class RadiusDrawable extends ShapeDrawable {

        /**
         * 构建一个带圆角的Drawable
         *
         * @param color       背景纯色（这种圆角背景图片一般都是纯色）
         * @param topLeft     左上方圆角
         * @param topRight    右上方圆角
         * @param bottomLeft  左下方圆角
         * @param bottomRight 右下方圆角
         */
        public RadiusDrawable(int color, float topLeft, float topRight, float bottomLeft, float bottomRight) {

            RoundRectShape roundRectShape = new RoundRectShape(
                    new float[]{topLeft, topLeft, topRight, topRight, bottomLeft,
                            bottomLeft, bottomRight, bottomRight}, null, null);

            getPaint().setColor(color);
            setShape(roundRectShape);
        }

        @Override
        public int getIntrinsicHeight() {
            Rect rect = getBounds();
            if (rect != null) {
                return rect.height();
            }
            return super.getIntrinsicHeight();
        }

        @Override
        public int getIntrinsicWidth() {
            Rect rect = getBounds();
            if (rect != null) {
                return rect.width();
            }
            return super.getIntrinsicWidth();
        }
    }

    /**
     * 根据指定的图像路径和大小来获取缩略图 此方法有两点好处： 1.
     * 使用较小的内存空间，第一次获取的bitmap实际上为null，只是为了读取宽度和高度，
     * 第二次读取的bitmap是根据比例压缩过的图像，第三次读取的bitmap是所要的缩略图。 2.
     * 缩略图对于原图像来讲没有拉伸，这里使用了2.2版本的新工具ThumbnailUtils，使 用这个工具生成的图像不会被拉伸。
     * @param imagePath 图像的路径
     * @param width 指定输出图像的宽度
     * @param height 指定输出图像的高度
     * @return 生成的缩略图
     */
    public static Bitmap getImageThumbnail(String imagePath, int width, int height) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 获取这个图片的宽和高，注意此处的bitmap为null
        try
        {
            bitmap = BitmapFactory.decodeFile(imagePath, options);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        options.inJustDecodeBounds = false; // 设为 false
        // 计算缩放比
        int h = options.outHeight;
        int w = options.outWidth;
        int beWidth = w / width;
        int beHeight = h / height;
        int be = 1;
        if (beWidth < beHeight)
        {
            be = beWidth;
        } else
        {
            be = beHeight;
        }
        if (be <= 0)
        {
            be = 1;
        }
        options.inSampleSize = be;
        // 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        // 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    /**
     * 字节转换成图片
     * @param b
     * @return
     */
    public static Bitmap bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else
        {
            return null;
        }
    }

    /**
     * 将图片写入到磁盘
     *
     * @param img      图片数据流
     * @param pathAndPath 文件保存时的完整路径，包括文件名
     */
    public static void writeImageToDisk(byte[] img, String pathAndPath) {
        try {
            File file = new File(pathAndPath);
            FileOutputStream fops = new FileOutputStream(file);
            fops.write(img);
            fops.flush();
            fops.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *TODO: 将文件从旧位置转移到新位置
     * @param srcPathName 老路径
     * @param destPathName 将要移动的新路径
     */
    public static boolean copyFileFromP1ToP2(String srcPathName, String destPathName){
        File srcFile = null;
        File destFile = null ;
        try {
            srcFile = new File(srcPathName);
            if(!srcFile.exists() || !srcFile.isFile())
                return false;
            destFile = new File(destPathName);
            try {
                destFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        try {
            InputStream inputStream = new FileInputStream(srcFile);
            OutputStream outputStream = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024*1024];
            int readed = 0;
            while ((readed = inputStream.read(buffer,0,buffer.length)) != -1)
            {
                outputStream.write(buffer,0,readed);
            }
            inputStream.close();
            outputStream.close();
            srcFile.delete();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
