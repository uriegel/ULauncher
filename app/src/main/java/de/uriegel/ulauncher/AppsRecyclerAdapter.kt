package de.uriegel.ulauncher

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import de.uriegel.ulauncher.databinding.AppItemBinding

class AppsRecyclerAdapter(private val apps: List<AppDetail>)
    : RecyclerView.Adapter<AppsRecyclerAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return apps.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.app = apps[position]
        holder.binding.executePendingBindings()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AppItemBinding.inflate(
            LayoutInflater
                .from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    class ViewHolder(val binding: AppItemBinding)
        : RecyclerView.ViewHolder(binding.root)
}