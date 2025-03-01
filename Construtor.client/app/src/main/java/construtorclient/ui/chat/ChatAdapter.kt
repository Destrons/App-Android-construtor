package com.construtur.client.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.construtur.client.R
import com.construtur.client.model.Message

class ChatAdapter(private val messages: List<Message>) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].remetenteId == userId) R.layout.item_message_sent else R.layout.item_message_received
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemCount(): Int = messages.size

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textMessage: TextView? = itemView.findViewById(R.id.tvMensagem)
        private val imageMessage: ImageView? = itemView.findViewById(R.id.ivImagem)

        fun bind(message: Message) {
            if (message.mensagem.startsWith("http")) {
                imageMessage?.visibility = View.VISIBLE
                textMessage?.visibility = View.GONE
                Glide.with(itemView.context).load(message.mensagem).into(imageMessage!!)
            } else {
                imageMessage?.visibility = View.GONE
                textMessage?.visibility = View.VISIBLE
                textMessage?.text = message.mensagem
            }
        }
    }
}
