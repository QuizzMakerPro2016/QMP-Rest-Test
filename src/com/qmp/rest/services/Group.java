package com.qmp.rest.services;

import javax.ws.rs.Path;

import com.qmp.rest.models.KGroupe;

@Path("/group")
public class Group extends CrudRestBase {
	public Group() {
		kobjectClass = KGroupe.class;
		displayName = "group";
	}
}