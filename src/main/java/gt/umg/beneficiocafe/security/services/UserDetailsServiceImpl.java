package gt.umg.beneficiocafe.security.services;

import gt.umg.beneficiocafe.models.BCUsuarios;
import gt.umg.beneficiocafe.repository.UsuariosRepository;
import gt.umg.beneficiocafe.models.BCUsuarios;
import gt.umg.beneficiocafe.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UsuariosRepository usuariosRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BCUsuarios user = usuariosRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("NO se encontro al usuario: " + username));

        return UserDetailsImpl.build(user);
    }
}
