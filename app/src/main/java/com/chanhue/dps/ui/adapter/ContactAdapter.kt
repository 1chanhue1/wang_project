import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chanhue.dps.R
import com.chanhue.dps.databinding.ItemContactBinding
import com.chanhue.dps.databinding.ItemContactGridBinding
import com.chanhue.dps.model.Contact

class ContactAdapter(
    private var contactList: List<Contact>,
    private val isGridLayout: Boolean,
    private val toggleFavoriteCallback: (Contact) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onItemClick: ((Contact) -> Unit)? = null

    enum class ViewType {
        LINEAR, GRID
    }

    override fun getItemViewType(position: Int): Int {
        return if (isGridLayout) ViewType.GRID.ordinal else ViewType.LINEAR.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == ViewType.LINEAR.ordinal) {
            val binding = ItemContactBinding.inflate(inflater, parent, false)
            LinearViewHolder(binding)
        } else {
            val binding = ItemContactGridBinding.inflate(inflater, parent, false)
            GridViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val contact = contactList[position]
        when (holder) {
            is LinearViewHolder -> holder.bind(contact)
            is GridViewHolder -> holder.bind(contact)
        }
    }

    override fun getItemCount(): Int = contactList.size

    inner class LinearViewHolder(private val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(contact: Contact) {
            binding.nameTextView.text = itemView.context.getString(
                R.string.format_item_name,
                contact.petProfile.name,
                contact.owner.name
            )
            binding.speciesTextView.text = itemView.context.getString(
                R.string.format_item_description,
                contact.petProfile.species,
                contact.petProfile.age.toString()
            )
            // 썸네일 이미지와 즐겨찾기 아이콘 설정
            Glide.with(binding.thumbnailImageView.context)
                .load(contact.petProfile.thumbnailImage)
                .into(binding.thumbnailImageView)

            binding.ivFavorite.setImageResource(
                if (contact.isFavorite) R.drawable.ic_favorite_full else R.drawable.ic_heart_empty
            )

            binding.ivFavorite.setOnClickListener {
                toggleFavoriteCallback(contact)
            }

            itemView.setOnClickListener {
                onItemClick?.invoke(contact)
            }
        }
    }

    inner class GridViewHolder(private val binding: ItemContactGridBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(contact: Contact) {
            with(binding) {
                Glide.with(ivContactGridThumbnailImage.context)
                    .load(contact.petProfile.thumbnailImage)
                    .into(ivContactGridThumbnailImage)

                tvContactGridName.text = itemView.context.getString(
                    R.string.format_item_name,
                    contact.petProfile.name,
                    contact.owner.name
                )
                tvContactGridDescription.text = itemView.context.getString(
                    R.string.format_item_description,
                    contact.petProfile.species,
                    contact.petProfile.age.toString()
                )

                ivFavorite.setImageResource(
                    if (contact.isFavorite) R.drawable.ic_favorite_full else R.drawable.ic_heart_empty
                )

                ivFavorite.setOnClickListener {
                    toggleFavoriteCallback(contact)
                }

                itemView.setOnClickListener {
                    onItemClick?.invoke(contact)
                }
            }
        }
    }

    fun updateContacts(newContacts: List<Contact>) {
        contactList = newContacts
        notifyDataSetChanged()
    }

//    fun updateContacts(newContacts: List<Contact>) {
//        val diffCallback = ContactDiffCallback(contactList, newContacts)
//        val diffResult = DiffUtil.calculateDiff(diffCallback)
//
//        contactList = newContacts
//        diffResult.dispatchUpdatesTo(this)
//    }
}

class ContactDiffCallback(
    private val oldList: List<Contact>,
    private val newList: List<Contact>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
