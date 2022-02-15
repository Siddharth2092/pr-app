package com.example.prmanager.ui.adapter

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.prmanager.R
import com.example.prmanager.data.remote.model.PRResponse
import com.example.prmanager.utils.AndroidUtils
import com.example.prmanager.utils.Constants
import com.squareup.picasso.Picasso

class PRListAdapter(private val context: Context) :
    ListAdapter<PRResponse, PRListAdapter.PRListViewHolder>(PRListDiffCallback) {

  class PRListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val userNameTv: TextView = view.findViewById(R.id.pr_user_name)
    val createdOnTv: TextView = view.findViewById(R.id.crated_on)
    val closedOnTv: TextView = view.findViewById(R.id.closed_on)
    val titleTv: TextView = view.findViewById(R.id.pr_title)
    val userImgIv: ImageView = view.findViewById(R.id.pr_user_img)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PRListViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.pr_row_item, parent, false)
    return PRListViewHolder(view)
  }

  override fun onBindViewHolder(holder: PRListViewHolder, position: Int) {

    val pr = getItem(position)

    holder.userNameTv.text = pr.user.userName
    holder.titleTv.text = pr.title
    holder.createdOnTv.text = context.resources.getString(R.string.pr_created_on,
      AndroidUtils.formatDate(pr.createdOn, SimpleDateFormat(Constants.PR_DATE_FORMAT)))
    holder.closedOnTv.text = context.resources.getString(R.string.pr_closed_on,
      AndroidUtils.formatDate(pr.closedOn, SimpleDateFormat(Constants.PR_DATE_FORMAT)))

    Picasso.get().load(pr.user.imageUrl).into(holder.userImgIv)
  }
}

object PRListDiffCallback : DiffUtil.ItemCallback<PRResponse>() {
  override fun areItemsTheSame(oldItem: PRResponse, newItem: PRResponse): Boolean {
    return oldItem == newItem
  }

  override fun areContentsTheSame(oldItem: PRResponse, newItem: PRResponse): Boolean {
    return oldItem.id == newItem.id && oldItem.updatedAOn == newItem.updatedAOn
  }
}