package com.example.acer.zhihutext;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;

    private TextView mTv_title;
    private LinearLayout mLl_remind;
    private ImageButton mIb_search;
    private ImageButton mIb_remind;
    private ImageButton mIb_more;

    private int[] imagesEnableId =
            {R.drawable.rb_home_enable, R.drawable.rb_find_enable, R.drawable.rb_focus_enable,
                    R.drawable.rb_collection_enable, R.drawable.rb_draft_enable, R.drawable.rb_question_enable};
    private int[] imagesId =
            {R.drawable.rb_home, R.drawable.rb_find, R.drawable.rb_focus,
                    R.drawable.rb_collection, R.drawable.rb_draft, R.drawable.rb_question};
    private String[] titles = {"首页", "发现", "关注", "收藏", "草稿", "提问"};
    private ListView mLv_titles;

    // 默认当前被选中的item的位置为0
    private int mCurrentPos = 0;
    private MyAdapter mMyAdapter;
    private RelativeLayout mRl_left_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化控件
        initView();

        // 初始化数据
        initData();

    }

    // 初始化对象
    private void initView() {
        // 初始化Toolbar、DrawerLayout，生成相应的对象
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mRl_left_content = (RelativeLayout) findViewById(R.id.rl_left_content);

        // 初始化TextView标题、消息提醒控件容器，切换时需要修改标题、隐藏消息提醒控件
        mTv_title = (TextView) findViewById(R.id.tv_title);
        mLl_remind = (LinearLayout) findViewById(R.id.ll_remind);

        // 初始化标题栏ImageButton控件，并设置点击事件（在上面MainActivity中implements View.OnClickListener）
        mIb_search = (ImageButton) findViewById(R.id.ib_search);
        mIb_remind = (ImageButton) findViewById(R.id.ib_remind);
        mIb_more = (ImageButton) findViewById(R.id.ib_more);
        mIb_search.setOnClickListener(this);
        mIb_remind.setOnClickListener(this);
        mIb_more.setOnClickListener(this);

        // 初始化ListView
        mLv_titles = (ListView) findViewById(R.id.lv_title);
        mMyAdapter = new MyAdapter();
        mLv_titles.setAdapter(mMyAdapter);

        // 初始化抽屉菜单的剩下的三个控件并设置点击事件
        RelativeLayout rl_photo =  (RelativeLayout) findViewById(R.id.rl_photo);
        RelativeLayout rl_setting = (RelativeLayout) findViewById(R.id.rl_setting);
        RelativeLayout rl_theme = (RelativeLayout) findViewById(R.id.rl_theme);
        rl_photo.setOnClickListener(this);
        rl_setting.setOnClickListener(this);
        rl_theme.setOnClickListener(this);

        // 给ListView设置点击事件
        onItemClickListener();
    }

    // 设置应用title
    private void initData() {
        // 设置Toolbar标题，需在setSupportActionBar之前，不然会失效
        mToolbar.setTitle("首页");
        mToolbar.setTitleTextColor(Color.TRANSPARENT);

        // 设置toolbar支持actionbar
        setSupportActionBar(mToolbar);

        // 实现按钮开关的显示及打开关闭功能并同步动画
        initDrawerToggle();
    }

    private void initDrawerToggle() {
        // 参数：开启抽屉的activity、DrawerLayout的对象、toolbar按钮打开关闭的对象、描述open drawer、描述close drawer
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mTv_title.setText("知乎");
                mLl_remind.setVisibility(View.GONE);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                mTv_title.setText("首页");
                mLl_remind.setVisibility(View.VISIBLE);
            }
        };
        // 添加抽屉按钮，通过点击按钮实现打开和关闭功能; 如果不想要抽屉按钮，只允许在侧边边界拉出侧边栏，可以不写此行代码
        drawerToggle.syncState();
        // 设置按钮的动画效果; 如果不想要打开关闭抽屉时的箭头动画效果，可以不写此行代码
        mDrawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_search:
                Toast.makeText(getApplicationContext(), "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ib_remind:
                Toast.makeText(getApplicationContext(), "消息", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ib_more:
                Toast.makeText(getApplicationContext(), "更多", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_photo:
                mDrawerLayout.closeDrawer(mRl_left_content);
                Toast.makeText(getApplicationContext(), "个人主页", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_setting:
                mDrawerLayout.closeDrawer(mRl_left_content);
                Toast.makeText(getApplicationContext(), "设置", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_theme:
                mDrawerLayout.closeDrawer(mRl_left_content);
                Toast.makeText(getApplicationContext(), "切换主题", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public Object getItem(int position) {
            return titles[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getApplicationContext(), R.layout.left_list, null);
            ImageView iv_photo = (ImageView) view.findViewById(R.id.iv_photo);
            TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
            RelativeLayout rl_list = (RelativeLayout) view.findViewById(R.id.rl_list);

            tv_title.setText(titles[position]);

            if(mCurrentPos == position){
                // 当前条目被选中时，设置为可用，文字成白色、背景为蓝色、图片为白色
                tv_title.setEnabled(true);
                rl_list.setBackgroundColor(Color.rgb(35, 154, 237));
                iv_photo.setBackgroundResource(imagesEnableId[position]);
            } else {
                // 当前条目未选中时，设置为不可用，文字成黑色、无背景颜色、图片为彩色
                tv_title.setEnabled(false);
                rl_list.setBackgroundColor(Color.TRANSPARENT);
                iv_photo.setBackgroundResource(imagesId[position]);
            }
            return view;
        }
    }

    private void onItemClickListener() {
        mLv_titles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 更新当前被选中的位置
                mCurrentPos = position;
                // 刷新listview
                mMyAdapter.notifyDataSetChanged();

                mDrawerLayout.closeDrawer(mRl_left_content);
            }
        });
    }

}
