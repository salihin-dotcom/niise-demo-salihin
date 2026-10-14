package my.gov.imi.niisesali_demo.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import my.gov.imi.niisesali_demo.Pengguna;
import my.gov.imi.niisesali_demo.PenggunaDTO;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        
        // Tells ModelMapper to map the incoming plain text password into the entity's hashPassword target
        mapper.addMappings(new PropertyMap<PenggunaDTO, Pengguna>() {
            @Override
            protected void configure() {
                map().setHashPassword(source.getPassword());
            }
        });
        
        return mapper;
    }
}