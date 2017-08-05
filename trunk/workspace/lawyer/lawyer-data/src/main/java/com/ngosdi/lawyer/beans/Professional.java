package com.ngosdi.lawyer.beans;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PROFESSIONAL")
@DiscriminatorValue("PROFESSIONAL")
public class Professional extends Actor {

	private static final long serialVersionUID = 1L;

}
