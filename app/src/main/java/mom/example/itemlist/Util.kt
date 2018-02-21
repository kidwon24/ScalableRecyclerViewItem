package mom.example.scalablerecyclerviewitem

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class Util () {
    fun storeImg(appContext: Context, img: NamedBitmap, dirPath: String, external: Boolean = false,
                 compressFormat: Bitmap.CompressFormat=Bitmap.CompressFormat.JPEG, imgQuality: Int = 74) {

        val dir = when(external) {
            true -> getExternalAlbumStorage(dirPath)
            false -> getInternalAlbumStorage(dirPath, appContext)
        }

        if (!dir.exists()) {
            dir.mkdirs()
        }

        val filepath = File(dir, "${img.name}${when(compressFormat){
            Bitmap.CompressFormat.JPEG -> ".jpg"
            Bitmap.CompressFormat.PNG -> ".png"
            else -> ""
        }}")

        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(filepath)
            img.bitmap.compress(compressFormat, imgQuality, fos)
        } catch (ex: Exception) {
            Log.e("write img", "Shit happens")
            // TODO: handle exception
        } finally {
            try {
                fos!!.close()
            } catch (ex: Exception) {
                // TODO: handle exception
            }
        }
    }

    fun loadStorageImg(appContext: Context, dirPath: String, imgName: String, external: Boolean = false) : Bitmap {
        val dir = when(external) {
            true -> getExternalAlbumStorage(dirPath)
            false -> getInternalAlbumStorage(dirPath, appContext)
        }
        if(!dir.exists()){
            Log.e("ImageLoader","Error obtaining directory: $dir")
            return Bitmap.createBitmap(null)
        }

        var fis: FileInputStream? = null
        try {
            fis = FileInputStream(File(dir, imgName))
            return BitmapFactory.decodeStream(fis)
        } catch (ex: Exception) {

        } finally {
            try {
                fis!!.close()
            } catch (ex: Exception) {
                // TODO: handle exception
            }
        }

        return  Bitmap.createBitmap(null)
    }

    fun getInternalAlbumStorage(albumName: String, appContext: Context) : File{
        val dirs =  albumName.split("/")
        val cw  = ContextWrapper(appContext)
        var dir = cw.getDir(dirs.first(), Context.MODE_PRIVATE)

        if (1 < dirs.size) {
            dir = File(dir, dirs.drop(1).joinToString("/"))
        }

        return dir
    }

    fun isExternalStorageWritable(): Boolean {
        val state = Environment.getExternalStorageState()
        if (Environment.MEDIA_MOUNTED == state) {
            return true
        }
        return false
    }

    /* Checks if external storage is available to at least read */
    fun isExternalStorageReadable(): Boolean {
        val state = Environment.getExternalStorageState()
        if (Environment.MEDIA_MOUNTED == state || Environment.MEDIA_MOUNTED_READ_ONLY == state) {
            return true
        }
        return false
    }

    fun checkPermissionForWriteExtertalStorage(activity: Activity): Boolean {

        val context = activity.application.applicationContext
        var result = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            result = context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

            if (!result) {
                requestPermissionForWriteExternalStorage(activity)
            }
        }
        return result
    }

    fun checkPermissionForReadExtertalStorage(activity: Activity): Boolean {

        val context = activity.application.applicationContext
        var result = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            result = context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

            if (!result) {
                requestPermissionForReadExternalStorage(activity)
            }
        }
        return result
    }

    @Throws(Exception::class)
    fun requestPermissionForReadExternalStorage(activity: Activity) {
        try {
            ActivityCompat.requestPermissions(activity as Activity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    100)
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }

    }

    @Throws(Exception::class)
    fun requestPermissionForWriteExternalStorage(activity: Activity) {
        try {
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    101)
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }

    }

    fun getActivity(context: Context) : Activity? {
        var tempContext = context
        while (tempContext is ContextWrapper) {
            if (tempContext is Activity) {
                return tempContext as Activity
            }
            tempContext = (context as ContextWrapper).baseContext
        }

        return null
    }

    private fun getExternalAlbumStorage(albumName: String): File {
        return File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName)
    }

}

enum class RecourceTypes {
    DRAWABLE;

    fun getNameSmall() : String {
        return name.toLowerCase()
    }
}