package co.mewi.sample.valarmorghulis.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.mewi.sample.valarmorghulis.R
import co.mewi.sample.valarmorghulis.model.Character
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_card.view.*

class CharacterAdapter(var context: Context, var list: MutableList<Character>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(view: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return CharacterViewHolder(
            LayoutInflater.from(view.context).inflate(
                R.layout.item_card,
                view,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        Glide
            .with(context)
            .load(list[position].photo)
            .apply(RequestOptions.circleCropTransform())
            .into((viewHolder as CharacterViewHolder).imageView)

        viewHolder.name.text = list[position].name
        viewHolder.age.text = String.format("Age: %d", list[position].age)
        viewHolder.height.text = String.format("Height: %sm", list[position].height)
        viewHolder.popularity.text = String.format("Popularity: %d%%", list[position].popularity)
    }

    class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView = view.card_image
        val name = view.card_name
        val age = view.card_age
        val height = view.card_height
        val popularity = view.card_popularity
    }
}