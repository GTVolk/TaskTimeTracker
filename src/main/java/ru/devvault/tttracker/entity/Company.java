package ru.devvault.tttracker.entity;

import lombok.*;

import java.util.List;
import java.util.Objects;
import javax.json.JsonObjectBuilder;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "ttt_company", schema = "task_time_tracker")
@NamedQueries({
    @NamedQuery(name = "Company.findAll", query = "SELECT c FROM Company c ORDER BY c.companyName ASC "),
    @NamedQuery(name = "Company.findByIdCompany", query = "SELECT c FROM Company c WHERE c.idCompany = :idCompany"),
    @NamedQuery(name = "Company.findByCompanyName", query = "SELECT c FROM Company c WHERE c.companyName = :companyName")})
public class Company extends AbstractEntity implements EntityItem<Integer> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_company")
    private Integer idCompany;

    @NonNull
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "company_name")
    private String companyName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
    private List<Project> projects;

    @Override
    public int hashCode() {

        int hash = 0;
        hash += (idCompany != null ? idCompany.hashCode() : 0);

        return hash;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final Company other = (Company) obj;

        return Objects.equals(this.idCompany, other.idCompany);
    }

    @Override
    public String toString() {
        return "ru.devvault.tttracker.entity.Company[ idCompany=" + idCompany + " ]";
    }

    @Override
    public Integer getId() {
        return idCompany;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        builder.add("idCompany", idCompany)
           .add("companyName", companyName);
    }
}
