import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chanhue.dps.R
import com.chanhue.dps.databinding.ItemContactBinding
import com.chanhue.dps.model.Contact

class ContactAdapter(
    private var contactList: List<Contact>,
    private val toggleFavoriteCallback: (Contact) -> Unit
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    var onItemClick: ((Contact) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contactList[position]
        holder.bind(contact)
    }

    override fun getItemCount(): Int = contactList.size

    inner class ContactViewHolder(private val binding: ItemContactBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contact) {
            binding.nameTextView.text = contact.owner.name
            binding.speciesTextView.text = contact.petProfile.species
            binding.ageTextView.text = contact.petProfile.age.toString()
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

    fun updateContacts(newContacts: List<Contact>) {
        contactList = newContacts
        notifyDataSetChanged()
    }
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
