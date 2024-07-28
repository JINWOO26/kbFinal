package com.example.kbfinal.service;

import com.example.kbfinal.entity.User;
import com.example.kbfinal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EncryptService encryptService;


    public void registerUser(User user) {
        // 비밀번호를 암호화하여 저장

        User regUser = new User();

        // password를 인코딩
        //String password = passwordEncoder.encode(user.getPassword());
        String password = encryptService.encryptPassword(user.getPassword());

        // user entity에 인코딩 된 password를 넣기
        regUser.setPassword(password);
        regUser.setUsername(user.getUsername());
        regUser.setId(user.getId());

        userRepository.save(regUser);
    }

    public boolean authenticate(String username, String password) {
        // 사용자 조회
        User user = userRepository.findByUsername(username); // 직접 repo에서 구현
        if (user == null) {
            return false;
        }
        // 입력된 비밀번호와 저장된 암호화된 비밀번호를 비교
        //return passwordEncoder.matches(password, user.getPassword());

        if(encryptService.decryptPassword(user.getPassword()).equals(password)){
            return true;
        }else return false;

    }

    //id조회
    public boolean findById(Long id){
        if(userRepository.existsById(id) == true) return true;
        else return false;
    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }

    public List<User> findUsers(){
        return userRepository.findAll();
    }

    public Long usercount(){
        return userRepository.count();
    }

    // 이후 컨트롤러에서 들어오게 될  내용 추가 구현하기
}
