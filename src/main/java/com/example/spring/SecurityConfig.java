package com.example.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration//設定クラスであることを示すアノテーション
@EnableWebSecurity//spring securityが有効になるらしい
public class SecurityConfig extends WebSecurityConfigurerAdapter{//WebSecurityConfigurerAdapterにはSpring Securityの設定情報が定義されており、対象のメソッドをオーバーライドすることで設定を変更することができます。


	@Override
	protected void configure(HttpSecurity http)throws Exception{
		http.authorizeRequests()//対象リクエストを指定
				.antMatchers("/","/accountCreate","/accountAll","/css/**","/imgs/**").permitAll()//認証しなくてもアクセスできるパラメータ
				.antMatchers().hasRole("ADMIN")//ADMIN権限をもったユーザーのみアクセスできるように制限
				.anyRequest().authenticated()//.そのほかのリクエストは.認証が必要とする
				.and()
				.formLogin()
				.defaultSuccessUrl("/account")//ログイン成功後の遷移ページ.第2引数にtrueを設定した場合、常に第1引数のURLへ移動します。falseを設定した場合、認証前に遷移しようとしていたURLへ移動します。認証に失敗するとfailureUrl()で設定したURLへ遷移します。
				.and().httpBasic()
				.and().csrf().csrfTokenRepository(new CookieCsrfTokenRepository()).disable();
	}

	@Autowired
	UserDetailsServiceImpl service;//ユーザー情報をもつUserDetailsServiceImplのインスタンスを作成

	//ユーザー情報に関する設定を行う
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(service);//userDetailsServiceにユーザー情報を設定する

//インラインメモリに簡易的にユーザー情報を登録する実装
//		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();//パスワードをハッシュ化するための処理
//		auth.inMemoryAuthentication()
//				.withUser("user").password(encoder.encode("password")).roles("USER")
//				.and()
//				.withUser("admin").password(encoder.encode("password")).roles("ADMIN");
	}

}
