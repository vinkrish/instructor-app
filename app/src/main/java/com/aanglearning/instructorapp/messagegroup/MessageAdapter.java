package com.aanglearning.instructorapp.messagegroup;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aanglearning.instructorapp.R;
import com.aanglearning.instructorapp.model.Message;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Vinay on 07-04-2017.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Message> messages;

    private static final int ITEM_TYPE_TEXT = 0;
    private static final int ITEM_TYPE_IMAGE = 1;

    ColorGenerator generator = ColorGenerator.MATERIAL;
    TextDrawable.IBuilder builder = TextDrawable.builder()
            .beginConfig()
            .withBorder(4)
            .endConfig()
            .round();

    MessageAdapter(Context context, ArrayList<Message> messages) {
        this.mContext = context;
        this.messages = messages;
    }

    ArrayList<Message> getDataSet() {
        return messages;
    }

    @UiThread
    void setDataSet(ArrayList<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    @UiThread
    void updateDataSet(ArrayList<Message> msgs) {
        //ArrayList<Message> oldMessages = messages;
        //this.messages = new ArrayList<>();
        //this.messages.addAll(oldMessages);
        int pos = messages.size();
        this.messages.addAll(msgs);
        notifyItemRangeInserted(pos, messages.size() - 1);
    }

    @UiThread
    void insertDataSet(Message message) {
        this.messages.add(0, message);
        notifyItemInserted(0);
    }

    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_TEXT) {
            View textView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_text_item, parent, false);
            return new TextHolder(textView);
        } else {
            View imgView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_image_item, parent, false);
            return new ImageHolder(imgView);
        }
    }

    @Override
    public void onBindViewHolder(MessageAdapter.ViewHolder holder, int position) {
        final int itemType = getItemViewType(position);

        if (itemType == ITEM_TYPE_TEXT) {
            ((TextHolder)holder).bind(messages.get(position));
        } else if (itemType == ITEM_TYPE_IMAGE) {
            ((ImageHolder)holder).bind(messages.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).getMessageType().equals("text")) {
            return ITEM_TYPE_TEXT;
        } else {
            return ITEM_TYPE_IMAGE;
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(View v) {
            super(v);
        }
    }

    class TextHolder extends ViewHolder {
        @BindView(R.id.image_view) ImageView senderImage;
        @BindView(R.id.sender_name) TextView senderName;
        @BindView(R.id.created_date) TextView createdDate;
        @BindView(R.id.message) TextView messageTV;

        TextHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bind(final Message message) {
            int color = generator.getColor(message.getSenderName());
            TextDrawable drawable = builder.build(message.getSenderName().substring(0,1), color);
            senderImage.setImageDrawable(drawable);
            senderName.setText(message.getSenderName());
            DateTime dateTime = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.S").parseDateTime(message.getCreatedAt());
            createdDate.setText(DateTimeFormat.forPattern("dd-MMM, HH:mm").print(dateTime));
            messageTV.setText(message.getMessageBody());
        }

    }

    class ImageHolder extends ViewHolder {
        @BindView(R.id.image_view) ImageView senderImage;
        @BindView(R.id.sender_name) TextView senderName;
        @BindView(R.id.created_date) TextView createdDate;
        @BindView(R.id.shared_image) ImageView sharedImage;

        ImageHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bind(final Message message) {
            int color = generator.getColor(message.getSenderName());
            TextDrawable drawable = builder.build(message.getSenderName().substring(0,1), color);
            senderImage.setImageDrawable(drawable);
            senderName.setText(message.getSenderName());
            createdDate.setText("07-04-2017 21:25");

            sharedImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    displayImageDialog(message);
                }
            });
            //sharedImage.setImageResource(R.drawable.books);
            Picasso.with(mContext)
                    .load(message.getImageUrl())
                    .placeholder(R.drawable.books)
                    .error(R.drawable.books)
                    .into(sharedImage);
        }

    }

    private Dialog displayImageDialog(final Message message) {
        final Dialog dialog = new Dialog(mContext, R.style.DialogFadeAnim);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_image_item);

        ImageView fullImage = (ImageView) dialog.findViewById(R.id.full_image);
        Picasso.with(mContext)
                .load(message.getImageUrl())
                .placeholder(R.drawable.books)
                .error(R.drawable.books)
                .into(fullImage);

        dialog.show();

        //Grab the window of the dialog, and change the width
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());

        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        return dialog;
    }

}
