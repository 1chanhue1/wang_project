import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chanhue.dps.R
import com.chanhue.dps.databinding.ItemContactBinding
import com.chanhue.dps.model.Contact

class ContactAdapter(private val contacts: List<Contact>) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    inner class ContactViewHolder(private val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(contact: Contact) {
            // petProfile에서 정보 추출
            val petProfile = contact.petProfile
            binding.nameTextView.text = petProfile.name
            binding.speciesTextView.text = petProfile.species
            binding.ageTextView.text = petProfile.age.toString()+" "
            Glide.with(binding.thumbnailImageView.context)
                .load(petProfile.thumbnailImage)
                .error(R.drawable.mypage_default_image) // 기본이미지 로드 실패 할때
                .into(binding.thumbnailImageView)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contacts[position])
    }

    override fun getItemCount(): Int {
        return contacts.size
    }
}
