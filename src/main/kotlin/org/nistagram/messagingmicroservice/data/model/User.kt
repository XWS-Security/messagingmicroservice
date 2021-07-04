package org.nistagram.messagingmicroservice.data.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
@Table(name = "NistagramUser")
data class User(
    @Id
    @SequenceGenerator(name = "user_sequence_generator", sequenceName = "user_sequence", initialValue = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence_generator")
    @Column(name = "id", unique = true)
    var id: Long = 0L,
    @Column(name = "username", unique = true)
    var nistagramUsername: String = "",
    var profilePrivate: Boolean = false,
    var messagesEnabled: Boolean = true,
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_role",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    var roles: List<Role> = listOf()
) : UserDetails {
    @Transient
    val administrationRole: String = "NISTAGRAM_USER_ROLE"

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        roles.map { role -> SimpleGrantedAuthority(role.name) }.toMutableList()

    override fun isEnabled(): Boolean = true

    override fun getUsername(): String = nistagramUsername

    override fun isCredentialsNonExpired(): Boolean = true

    override fun getPassword(): String = ""

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false

        if (id != other.id) return false
        if (nistagramUsername != other.nistagramUsername) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + nistagramUsername.hashCode()
        return result
    }


}