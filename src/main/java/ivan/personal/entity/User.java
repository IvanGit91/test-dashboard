package ivan.personal.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Component
@Scope("prototype")
@Table(name = "users")
@Data
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "is_management_user")
    private boolean isManagementUser;

    public User(String username, String password, String email, Boolean val) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.isManagementUser = val;
    }

    public void setUser(User user) {
        this.id = user.id;
        this.username = user.username;
        this.email = user.email;
        this.password = user.password;
        this.isManagementUser = user.isManagementUser();
    }
}
