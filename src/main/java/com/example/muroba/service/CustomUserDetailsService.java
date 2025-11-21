package com.example.muroba.service;

import com.example.muroba.dto.CustomUserDetails;
import com.example.muroba.entity.Member;
import com.example.muroba.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String useremail) throws UsernameNotFoundException {

        System.out.println("UserDetailsService -> CustomUserDetailsService -> loadUserByUsername");
        System.out.println("");
        Member findmember = memberRepository.findByEmail(useremail)
                .orElseThrow( () -> new EntityNotFoundException("사용자가 존재하지 않습니다"));
        System.out.println("loadUserByUsername findByEmail done");

//        return null;
        return new CustomUserDetails(findmember);
    }
}
