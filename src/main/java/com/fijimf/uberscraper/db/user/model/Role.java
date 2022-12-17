package com.fijimf.uberscraper.db.user.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;




    @Table(name = "role")
    public class Role implements GrantedAuthority {
        @Id
        private long id;

        @Column( "role")
        private String roleName;

        public Role() {
        }

        public Role(String roleName) {
            this.id=0L;
            this.roleName = roleName;
        }

        @Override
        public String getAuthority() {
            return roleName;
        }

        public static final Role ANONYMOUS = new Role("ANONYMOUS");
    }