 # User -> Posts
 
 - Retrieve all users - GET  /users
 - Create a User -      POST /users
 - Retrieve one user -  GET  /users/{id} -> /users/1
 - Delete a User -      DELETE /users/{id} -> /users/1
 ================================================================== 
 - Retrieve all posts for a user - GET /users/{id}/posts
 - Create a posts for a user     - POST /users/{id}/posts
 - Retrieve details of a post    - GET /users/{id}/posts/{posts_id}
 ================================================================== 

 # Table Structure
 create table user(
 	id integer not null,
	birth_date timestamp,
	name varchar(255),
	primary_key(id)
	
 ================================================================== 
 ## Post.java
 ------------
 # There is a 'ManyToOne' relationship between User and Post.
 # Add User field in Post and add @ManyToOne at Post.
 # Also add 'fetch=FetchType.LAZY
 
 ================================================================== 
 ## User.java
 -------------
 # A User have one to many relationship.
 # Add List<Post> in User Entiy and add @OneToMany annotation, mention the mapped attribute.
 # Mention the field that is owning the relationship.
 # We do not want to create relationship column in both User and Post. We have created the relationship column of User in Post
 # Add 'mappedBy <Name of the Field in Post>'
 ==================================================================
 There is a create table Post has an Id and also it has a foreign key called 'user_id <Integer>'
 The Post table has a link to User table by a 'UserId'
 
 A User can have multiple Posts and 
   
)