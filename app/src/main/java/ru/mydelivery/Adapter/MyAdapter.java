package ru.mydelivery.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ru.mydelivery.Pojo.Order;
import ru.mydelivery.Provider.VolleyRequest;
import ru.mydelivery.R;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Order> list = null;
    private Context context;
    private static Order order;
    private VolleyRequest volleyRequest;

    public MyAdapter(Context context, List<Order> list) {
        this.context = context;
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtJobsId;
        TextView txtName;
        TextView txtTelephone;
        TextView txtPick;
        TextView txtAddress;
        TextView txtDescription;
        TextView txtPrice;
        TextView txtNote;
        TextView txtDate;
        TextView txtUserId;
        TextView txtStatus;
        TextView txtStatusText;
        ImageView imgDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            txtJobsId = (TextView) itemView.findViewById(R.id.jobsId);
            txtName = (TextView) itemView.findViewById(R.id.name);
            txtTelephone = (TextView) itemView.findViewById(R.id.telephone);
            txtPick= (TextView) itemView.findViewById(R.id.pickFrom);
            txtAddress = (TextView) itemView.findViewById(R.id.address);
            txtDescription = (TextView) itemView.findViewById(R.id.description);
            txtPrice = (TextView) itemView.findViewById(R.id.price);
            txtNote = (TextView) itemView.findViewById(R.id.note);
            txtDate = (TextView) itemView.findViewById(R.id.date);
            txtUserId = (TextView) itemView.findViewById(R.id.userId);
            txtStatus = (TextView) itemView.findViewById(R.id.status);
            txtStatusText = (TextView) itemView.findViewById(R.id.statusText);
            imgDelete = (ImageView) itemView.findViewById(R.id.delete);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        volleyRequest = new VolleyRequest();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        order = list.get(position);
        int status = order.getStatus();
        holder.txtJobsId.setText("Номер задания: " + order.getJobsId());
        holder.txtName.setText("ФИО получателя:\n" + order.getName());
        holder.txtTelephone.setText("Номер телефона: " + order.getTelephone());
        holder.txtPick.setText("Откуда забрать: " + order.getPickFrom());
        holder.txtAddress.setText("Адрес получателя: " + order.getAddress());
        holder.txtDescription.setText("Описание товара: " + order.getDescription());
        holder.txtPrice.setText("Цена товара: " + order.getPrice() + " грн");
        holder.txtNote.setText("Примечание: " + order.getNote());
        holder.txtDate.setText("Дата доставки: " + order.getDate());
        holder.txtUserId.setText("Номер курьера: " + order.getUserId());
        holder.txtStatus.setText("Статус: " + getStatus(status));
        holder.txtStatusText.setVisibility(View.VISIBLE);
        switch (status) {
            case 0:
                holder.txtStatusText.setVisibility(View.GONE);
                break;
            case 1:
                holder.txtStatusText.setVisibility(View.GONE);
                break;
            case 2:
                holder.txtStatusText.setText("Получатель не все получил: " + order.getStatusText());
                break;
            case 3:
                holder.txtStatusText.setText("Причина отмены: " + order.getStatusText());
                break;
            case 4:
                holder.txtStatusText.setVisibility(View.GONE);
                break;
        }
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order = list.get(position);
                int jobsId = order.getJobsId();
                volleyRequest.deleteUser(context, jobsId);
                removeAt(position);
            }
        });

    }

    public String getStatus(int status) {
        String result = null;
        switch (status) {
            case 0:
                result = "Не выбрано";
                break;
            case 1:
                result = "Перенесен";
                break;
            case 2:
                result = "Частичный возврат";
                break;
            case 3:
                result = "Отменен";
                break;
            case 4:
                result = "Выполнено";
                break;
        }
        return result;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void removeAt(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list.size());
    }


}
