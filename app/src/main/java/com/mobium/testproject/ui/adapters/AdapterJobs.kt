package com.mobium.testproject.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobium.testproject.R
import com.mobium.testproject.model.JobItem
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.jobs_item.*


class AdapterJobs(val picasso: Picasso, val listener: (JobItem?) -> Unit) : RecyclerView.Adapter<AdapterJobs.MyViewHolder>() {

    private var items: List<JobItem>? = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.jobs_item,parent, false)

        return MyViewHolder(view = view)
    }

    override fun getItemCount(): Int {
        if (items == null)
            return 0
        return items!!.size
    }

    fun update(items: List<JobItem>?){
        this.items = items
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items?.get(position), listener)
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view), LayoutContainer{
        override val containerView: View?
            get() = itemView

        fun bind(jobItem: JobItem?, listener: (JobItem?) -> Unit){
            showLogo(jobItem?.companyLogo)
            showTitle(jobItem?.title)
            showCompanyName(jobItem?.company)
            showLocation(jobItem?.location)

            itemView.setOnClickListener {
                listener.invoke(jobItem)
            }
        }

        fun showLogo(url: String?){
            picasso.load(url).resize(65,65).into(ic_logo)
        }

        fun showTitle(s: String?){
            tv_title.text = s
        }

        fun showCompanyName(s: String?){
            tv_company.text = s
        }

        fun showLocation(s: String?){
            tv_location.text = s
        }
    }
}