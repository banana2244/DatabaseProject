

public class Tree {
    private int treeID;
    private int clientID;
    private int size;
    private int height;
    private String location;
    private String proximityToHouse;
    private String picture1;
    private String picture2;
    private String picture3;
	public String getPicture3() {
		return picture3;
	}
	public void setPicture3(String picture3) {
		this.picture3 = picture3;
	}
	public String getPicture2() {
		return picture2;
	}
	public void setPicture2(String picture2) {
		this.picture2 = picture2;
	}
	public String getPicture1() {
		return picture1;
	}
	public void setPicture1(String picture1) {
		this.picture1 = picture1;
	}
	public String getProximityToHouse() {
		return proximityToHouse;
	}
	public void setProximityToHouse(String proximityToHouse) {
		this.proximityToHouse = proximityToHouse;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getClientID() {
		return clientID;
	}
	public void setClientID(int clientID) {
		this.clientID = clientID;
	}
	public int getTreeID() {
		return treeID;
	}
	public void setTreeID(int treeID) {
		this.treeID = treeID;
	}

    // Constructors

	public Tree(int treeID, int clientID, int size, int height, String location, String proximityToHouse, String picture1, String picture2, String picture3) {
		this.treeID = treeID;
		this.clientID = clientID;
		this.size = size;
		this.height = height;
		this.location = location;
		this.proximityToHouse = proximityToHouse;
		this.picture1 = picture1;
		this.picture2 = picture2;
		this.picture3 = picture3;
	}

}
