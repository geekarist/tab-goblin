package com.github.geekarist.tabgoblin.server;

public class Tablature {

	private String contents;
	private int id;

	public Tablature(int id, String contents) {
		this.id = id;
		this.contents = contents;
	}

	public String getContents() {
		return contents;
	}

	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contents == null) ? 0 : contents.hashCode());
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tablature other = (Tablature) obj;
		if (contents == null) {
			if (other.contents != null)
				return false;
		} else if (!contents.equals(other.contents))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Tablature [contents=" + contents + ", id=" + id + "]";
	}

	public void setContents(String tabContents) {
		this.contents = tabContents;
	}
	
}
