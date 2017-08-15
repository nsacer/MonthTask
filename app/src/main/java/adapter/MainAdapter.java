package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.administrator.montht.R;
import com.haozhang.lib.SlantedTextView;

import java.util.ArrayList;

import helper.CardAdapterHelper;
import model.DayModel;

/**
 * Created by Administrator on 2017/8/13.
 * MainItemAdapter
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainItemViewHolder>
        implements View.OnClickListener{

    private final int TYPE_NONE = 0;
    private final int TYPE_ITEM = 1;

    private Context mContext;
    private CardAdapterHelper cardAdapterHelper = new CardAdapterHelper();
    private ArrayList<DayModel> models = new ArrayList<>();
    private OnMainItemClickListener onMainItemClickListener;

    public MainAdapter(Context context) {

        this.mContext = context;
    }

    public void setModels(ArrayList<DayModel> models) {

        if (models != null && !models.isEmpty()) {

            this.models = models;
            notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view) {

        if (onMainItemClickListener != null) {

            onMainItemClickListener.OnMainItemClick(view, (DayModel) view.getTag());
        }
    }

    public interface OnMainItemClickListener {

        void OnMainItemClick(View view, DayModel model);
    }

    @Override
    public int getItemViewType(int position) {
        return models.isEmpty() ? TYPE_NONE : TYPE_ITEM;
    }

    @Override
    public MainItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_adapter_main, parent, false);
        view.setOnClickListener(this);
        cardAdapterHelper.onCreateViewHolder(parent, view);
        return new MainItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainItemViewHolder holder, int position) {

        cardAdapterHelper.onBindViewHolder(holder.itemView, position, getItemCount());
        setDataToTarget(holder, position);
    }

    @Override
    public int getItemCount() {
        return models.isEmpty() ? 1 : models.size();
    }

    class MainItemViewHolder extends RecyclerView.ViewHolder {

        private ViewSwitcher vs;
        private TextView tvMoney, tvTime;
        private SlantedTextView stvLabel;

        MainItemViewHolder(View itemView) {
            super(itemView);

            vs = itemView.findViewById(R.id.vs_main_item);
            tvMoney = itemView.findViewById(R.id.tv_money_surplus_main_item);
            tvTime = itemView.findViewById(R.id.tv_time_main_item);
        }
    }

    private void setDataToTarget(MainItemViewHolder holder, int position) {

        int type = getItemViewType(position);
        holder.vs.setDisplayedChild(type == TYPE_NONE ? 1 : 0);

        if (type == TYPE_NONE)
            return;

        DayModel model = models.get(position);
        holder.itemView.setTag(model);
        holder.tvMoney.setText(String.valueOf(model.getMoneySurplus()));
        holder.tvTime.setText(model.getDayDate());
        holder.stvLabel.setText(model.isComplete() ? "已完成" : "进行中");
    }

    public void setOnMainItemClickListener(OnMainItemClickListener onMainItemClickListener) {
        this.onMainItemClickListener = onMainItemClickListener;
    }
}
