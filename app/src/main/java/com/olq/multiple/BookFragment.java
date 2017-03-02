package com.olq.multiple;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.olq.multiple.base.BaseFragment;

import java.io.File;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/2/21.
 */

public class BookFragment extends BaseFragment {


    @BindView(R.id.rv_recycler)
    RecyclerView rvRecycler;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;
    private BookAdapter bookAdapter;
    private List<Recommend.RecommendBooks> list;


    @Override
    public int getLayout() {
        return R.layout.fragment_book;
    }

    @Override
    public void onCreateView() {
        list = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvRecycler.setLayoutManager(manager);
        bookAdapter = new BookAdapter(getActivity(),list);

        rvRecycler.setAdapter(bookAdapter);
        srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
        queryFiles();
//        getFileName(SDCardUtils.getExternalStorageDirectory().listFiles());
//        if (SDCardUtils.isSDCardEnable()){
//            new Thread(){
//                @Override
//                public void run() {
//                    super.run();
////                    getAllFiles(SDCardUtils.getExternalStorageDirectory());
//                }
//            }.start();
//
//        }
    }

    private void getFileName(File[] files) {
        if (files != null) {// 先判断目录是否为空，否则会报空指针
            for (File file : files) {
                if (file.isDirectory()) {
//                    Log.i("zeng", "若是文件目录。继续读1" + file.getName().toString()
//                            + file.getPath().toString());

                    getFileName(file.listFiles());
//                    Log.i("zeng", "若是文件目录。继续读2" + file.getName().toString()
//                            + file.getPath().toString());
                } else {
                    String fileName = file.getName();
                    if (fileName.endsWith(".txt")) {
                        HashMap map = new HashMap();
                        String s = fileName.substring(0,
                                fileName.lastIndexOf(".")).toString();
                        Log.i("zeng", "文件名txt：：   " + s);
                        map.put("Name", fileName.substring(0,
                                fileName.lastIndexOf(".")));
//                        name.add(map);
                    }
                }
            }
        }
    }


    private void queryFiles() {
        String[] projection = new String[]{MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.SIZE
        };

        // cache
        String bookpath = createRootPath(getActivity());

        // 查询后缀名为txt与pdf，并且不位于项目缓存中的文档
        Cursor cursor = getActivity().getContentResolver().query(
                Uri.parse("content://media/external/file"),
                projection,
                MediaStore.Files.FileColumns.DATA + " not like ? and ("
                        + MediaStore.Files.FileColumns.DATA + " like ? or "
                        + MediaStore.Files.FileColumns.DATA + " like ? or "
                        + MediaStore.Files.FileColumns.DATA + " like ? or "
                        + MediaStore.Files.FileColumns.DATA + " like ? )",
                new String[]{"%" + bookpath + "%",
                        "%" + SUFFIX_TXT,
                        "%" + SUFFIX_PDF,
                        "%" + SUFFIX_EPUB,
                        "%" + SUFFIX_CHM}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int idindex = cursor.getColumnIndex(MediaStore.Files.FileColumns._ID);
            int dataindex = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA);
            int sizeindex = cursor.getColumnIndex(MediaStore.Files.FileColumns.SIZE);



            do {
                String path = cursor.getString(dataindex);

                int dot = path.lastIndexOf("/");
                String name = path.substring(dot + 1);
                if (name.lastIndexOf(".") > 0)
                    name = name.substring(0, name.lastIndexOf("."));

                Recommend.RecommendBooks books = new Recommend.RecommendBooks();
                books._id = name;
                books.path = path;
                books.title = name;
                books.isFromSD = true;
                books.lastChapter = formatFileSizeToString(cursor.getLong(sizeindex));

                list.add(books);
                bookAdapter.notifyDataSetChanged();
            } while (cursor.moveToNext());

            cursor.close();

//            bookAdapter.addAll(list);
        } else {
//            bookAdapter.clear();
        }
    }

    /**
     * 创建根缓存目录
     *
     * @return
     */
    public static String createRootPath(Context context) {
        String cacheRootPath = "";
        if (isSdCardAvailable()) {
            // /sdcard/Android/data/<application package>/cache
            cacheRootPath = context.getExternalCacheDir().getPath();
        } else {
            // /data/data/<application package>/cache
            cacheRootPath = context.getCacheDir().getPath();
        }
        return cacheRootPath;
    }

    public static boolean isSdCardAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }
    public static final String SUFFIX_TXT = ".txt";
    public static final String SUFFIX_PDF = ".pdf";
    public static final String SUFFIX_EPUB = ".epub";
    public static final String SUFFIX_ZIP = ".zip";
    public static final String SUFFIX_CHM = ".chm";

    public static class Recommend extends Base {

        public List<RecommendBooks> books;

        public static class RecommendBooks implements Serializable {

            /**
             * _id : 526e8e3e7cfc087140004df7
             * author : 太一生水
             * cover : /agent/http://image.cmfu.com/books/3347382/3347382.jpg
             * shortIntro : 十大封号武帝之一，绝世古飞扬在天荡山脉陨落，于十五年后转世重生，化为天水国公子李云霄，开启了一场与当世无数天才相争锋的逆天之旅。武道九重，十方神境，从此整个世界...
             * title : 万古至尊
             * hasCp : true
             * latelyFollower : 3053
             * retentionRatio : 42.59
             * updated : 2016-07-25T15:29:51.703Z
             * chaptersCount : 2406
             * lastChapter : 第2406章 千载风云尽付一笑（大结局）
             */

            public String _id;
            public String author;
            public String cover;
            public String shortIntro;
            public String title;
            public boolean hasCp;
            public boolean isTop = false;
            public boolean isSeleted = false;
            public boolean showCheckBox = false;
            public boolean isFromSD = false;
            public String path = "";
            public int latelyFollower;
            public double retentionRatio;
            public String updated = "";
            public int chaptersCount;
            public String lastChapter;
            public String recentReadingTime = "";

            @Override
            public boolean equals(Object obj) {
                if (obj instanceof RecommendBooks) {
                    RecommendBooks bean = (RecommendBooks) obj;
                    return this._id.equals(bean._id);
                }
                return super.equals(obj);
            }

            @Override
            public int hashCode() {
                return this._id.hashCode();
            }
        }
    }

    public static class Base implements Serializable {

        public boolean ok;
    }

    /**
     * 转换文件大小
     *
     * @param fileLen 单位B
     * @return
     */
    public static String formatFileSizeToString(long fileLen) {
        DecimalFormat df = new DecimalFormat("0.00");
        String fileSizeString = "";
        if (fileLen < 1024) {
            fileSizeString = df.format((double) fileLen) + "B";
        } else if (fileLen < 1048576) {
            fileSizeString = df.format((double) fileLen / 1024) + "K";
        } else if (fileLen < 1073741824) {
            fileSizeString = df.format((double) fileLen / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileLen / 1073741824) + "G";
        }
        return fileSizeString;
    }

}
