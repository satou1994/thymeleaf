package com.example.spring;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.spring.account.Account;
import com.example.spring.account.AccountRepository;


//ユーザー情報を作成するクラス
@Service//サービスレイヤを扱うクラス。@Serviceアノテーションが設定されると、クラスがDIコンテナへの登録対象となるらしい
public class UserDetailsServiceImpl implements UserDetailsService {//UserDetailsServiceがユーザー情報を検索する役割を担っている

	private AccountRepository repository;

	@Autowired
	public UserDetailsServiceImpl(AccountRepository repository) {
		this.repository = repository;
	}

	//UserDetailsServiceのソースを確認すると、宣言されているのはUserDetailsを返すloadUserByUsernameメソッドだけです。ここにユーザーの検索処理を実装します。
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

		if(username.isEmpty() || username == null) {
			throw new UsernameNotFoundException("username is empty");
		}

		Account account = repository.findByUsername(username);

		if(account == null) {
			throw new UsernameNotFoundException("Not found username : "+ username);
		}

		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(account.getRole()));

		User user = new User(account.getUsername(), account.getPassword(), authorities);

		return user;

//インラインメモリに簡易的にユーザー情報を作成する時
//		//adminだけ認証する
//		if(username == null || "admin".contentEquals(username)) {
//			throw new UsernameNotFoundException("Not Found username :" + username);
//		}
//		// パスワードの設定
//		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//		String password = encoder.encode("password");
//		//権限の設定
//		Collection<GrantedAuthority> authorities = new ArrayList<>();
//		authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//		//ユーザー情報を作成
//		User user = new User(username,password,authorities);
	}

}
