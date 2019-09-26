package com.pavelrekun.rekado.screens.payload_fragment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.data.Payload
import com.pavelrekun.rekado.services.Events
import com.pavelrekun.rekado.services.Logger
import com.pavelrekun.rekado.services.payloads.PayloadHelper
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_payload.*
import org.greenrobot.eventbus.EventBus
import java.io.File

class PayloadsAdapter(var data: MutableList<Payload>) : androidx.recyclerview.widget.RecyclerView.Adapter<PayloadsAdapter.ViewHolder>() {

    override fun getItemCount() = data.size

    fun updateList() {
        this.data = PayloadHelper.getAllPayloads()

        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_payload, parent, false)
        return ViewHolder(itemView)
    }

    class ViewHolder(override val containerView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(payload: Payload) {
            itemPayloadName.text = payload.name

            itemPayloadRemove.visibility = if (payload.name == PayloadHelper.BUNDLED_PAYLOAD_AMS || payload.name == PayloadHelper.BUNDLED_PAYLOAD_SX
                    || payload.name == PayloadHelper.BUNDLED_PAYLOAD_REINX || payload.name == PayloadHelper.BUNDLED_PAYLOAD_HEKATE) View.GONE else View.VISIBLE

            itemPayloadRemove.setOnClickListener {
                File(payload.path).delete()
                EventBus.getDefault().post(Events.UpdatePayloadsListEvent())
                Logger.info("Payload ${payload.name} deleted!")
            }
        }

    }
}
