package ggstore.com.fragment;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;

import ggstore.com.R;
import ggstore.com.base.BaseRecyclerAdapter;
import ggstore.com.base.BaseRecyclerViewFragment;
import ggstore.com.base.Constent;
import ggstore.com.bean.CourseBookBean;
import ggstore.com.utils.AppOperator;
import ggstore.com.utils.OkHttpManager;
import ggstore.com.utils.ToastUtils;
import okhttp3.Request;

public class ShopCartListRecycleFragment extends BaseRecyclerViewFragment {
    int page;
    @Override
    protected void requestData(final boolean isRefreshing) { //true 为刷新 false 为加载更多
        if (isRefreshing) {
            page = 1;
            AppOperator.runOnThread(new Runnable() {
                @Override
                public void run() {
                    String url = Constent.base_url + "api_get_coursebook.php?recordperpage=4&page=" + page +
                            "&sortby=&token=" + Constent.token + "&username=&lang=" + getString(R.string.api_lang);
                    OkHttpManager.getAsync(url, new OkHttpManager.DataCallBack() {
                        @Override
                        public void requestFailure(Request request, Exception e) {
                            ToastUtils.showToast("网络出错");
                            onRequestError();
                        }
                        @Override
                        public void requestSuccess(String result) throws Exception {
//                            ArrayList<CourseBookBean> list = parseData(result);
                            ArrayList<String> list = new ArrayList<>();
                            for (int i = 0; i < 10; i++) {
                                list.add("1");
                            }
                            mAdapter.resetItem(list);
                            onRequestSuccess();
                            mRefreshLayout.setEnabled(false);//设置不可刷新,以为购物车一般只加载一次
                            mRefreshLayout.setCanLoadMore(false);   //设置
                            mAdapter.setStateCustom("運費 : HK$50",Gravity.RIGHT);
                        }
                    });
                }
            });
        }
    }


    private ArrayList<CourseBookBean> parseData(String result) throws JSONException {
        ArrayList<CourseBookBean> courseBooList = new ArrayList<>();
        return courseBooList;
    }

    @Override
    protected BaseRecyclerAdapter getRecyclerAdapter() {
        final CourseAdapter courseAdapter = new CourseAdapter(getActivity(), BaseRecyclerAdapter.ONLY_FOOTER);
        return courseAdapter;
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(getActivity(), 1);
    }

    class CourseAdapter extends BaseRecyclerAdapter<String> {

        public CourseAdapter(Context context, int mode) {
            super(context, mode);
        }

        @Override
        protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
            CourseAdapter.MyViewHolder myViewHolder = new CourseAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.recycler_shop_cart_list_item, parent, false));
            return myViewHolder;
        }


        @Override
        protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, final String item, int position) {
            //TODO 绑定视图--->加上数据
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            public ImageView addShop;
            public ImageView imgDetail;
            public TextView title;
            public TextView oldPrice;
            public TextView newPrice;
            public TextView detail1;
            public TextView detail2;

            public MyViewHolder(View view) {
                super(view);
//                title = view.findViewById(R.id.title);
//                addShop = view.findViewById(R.id.add_shop);
//                oldPrice = view.findViewById(R.id.old_price);
//                newPrice = view.findViewById(R.id.new_price);
//                imgDetail = view.findViewById(R.id.img_detail);
//                detail1 = view.findViewById(R.id.detail1);
//                detail2 = view.findViewById(R.id.detail2);
            }
        }
    }
}
