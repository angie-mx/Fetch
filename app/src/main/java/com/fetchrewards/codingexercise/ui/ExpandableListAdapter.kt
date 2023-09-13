package com.fetchrewards.codingexercise.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.fetchrewards.codingexercise.data.Hiring
import com.fetchrewards.codingexercise.databinding.GroupViewBinding
import com.fetchrewards.codingexercise.databinding.ItemViewBinding

class ExpandableListAdapter(
    private val content: Map<String, List<Hiring>>
) : BaseExpandableListAdapter() {

    private val title: List<String> = ArrayList(content.keys)

    override fun getChild(listPosition: Int, expandedListPosition: Int): Any {
        return content[title[listPosition]]!![expandedListPosition].name
    }

    override fun getChildId(listPosition: Int, expandedListPosition: Int): Long {
        return expandedListPosition.toLong()
    }

    override fun getChildView(
        listPosition: Int, expandedListPosition: Int,
        isLastChild: Boolean, convertView: View?, parent: ViewGroup
    ): View {
        val binding = if (convertView != null) ItemViewBinding.bind(convertView)
        else ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.itemTextView.text = getChild(listPosition, expandedListPosition) as String
        return binding.root
    }

    override fun getChildrenCount(listPosition: Int): Int {
        return content[title[listPosition]]!!.size
    }

    override fun getGroup(listPosition: Int): Any {
        return title[listPosition]
    }

    override fun getGroupCount(): Int {
        return title.size
    }

    override fun getGroupId(listPosition: Int): Long {
        return listPosition.toLong()
    }

    override fun getGroupView(
        listPosition: Int, isExpanded: Boolean,
        convertView: View?, parent: ViewGroup
    ): View {
        val binding = if (convertView != null) GroupViewBinding.bind(convertView)
        else GroupViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.groupTextView.text = getGroup(listPosition) as String
        return binding.root
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean {
        return true
    }
}
