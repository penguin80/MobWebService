/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.comem.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

/**
 *
 * @author raphaelbaumann
 */
@NamedQuery(name="findMemberIdFromEmail", query="SELECT m.id FROM Authentication a JOIN a.authenticatedMember m WHERE m.email = :email")
@Entity
public class Authentication implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String email;
    private String password;
    @OneToOne(mappedBy="authenticate", fetch=FetchType.LAZY)
    private Membership authenticatedMember;
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Membership getAuthenticatedMember() {
        return authenticatedMember;
    }

    public void setAuthenticatedMember(Membership authenticatedMember) {
        this.authenticatedMember = authenticatedMember;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (email != null ? email.hashCode() : null);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Authentication)) {
            return false;
        }
        Authentication other = (Authentication) object;
        if ((this.email == null && other.email != null) || (this.email != null && !this.email.equals(other.email))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.comem.model.Authentication[ email=" + email + " ]";
    }
    
}
