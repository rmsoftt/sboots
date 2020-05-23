package deploy.jsk.service;

import deploy.jsk.entity.Users;

public interface UserService {
	public Users findUserByEmail(String email);
	public void saveUser(Users user);
}