package pl.sglebocki.spring.blog.entities;

import java.io.Serializable;

class CompositeKey implements Serializable {
	private static final long serialVersionUID = -1302539461914351298L;
	PostEntity post;
	UserEntity user;
}
