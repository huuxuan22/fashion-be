package com.example.projectc1023i1.service;

import com.example.projectc1023i1.Dto.AddressUserDTO;
import com.example.projectc1023i1.model.*;
import com.example.projectc1023i1.repository.impl.IAddressUserRepo;
import com.example.projectc1023i1.repository.impl.ICommuneRepo;
import com.example.projectc1023i1.repository.impl.IDistrictRepo;
import com.example.projectc1023i1.repository.impl.IProvinceRepo;
import com.example.projectc1023i1.service.impl.IAddressUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressUserService implements IAddressUserService {
    @Autowired
    private IAddressUserRepo addressUserRepository;

    @Autowired
    private IProvinceRepo provinceRepo;
    @Autowired
    private IDistrictRepo districtRepo;
    @Autowired
    private ICommuneRepo communeRepo;

    @Override
    public void addAddressUser(AddressUser addressUser) {
        addressUserRepository.save(addressUser);
    }

    @Override
    public List<AddressUser> getAddressUser(Integer userId) {
        List<AddressUser> addressUsers = addressUserRepository.findByUser(userId);
        return addressUsers;
    }

    @Override
    public void deleteAddressUser(Integer id) {
        addressUserRepository.deleteById(id);
    }

    @Override
    public void updateAddress(AddressUser addressUser,Users user) {
        Province province = provinceRepo.findProvinceByCode(addressUser.getProvince().getCode());
        if (province == null) {
            province = provinceRepo.save(addressUser.getProvince());
        }

        District district = districtRepo.findDistrictByCode(addressUser.getDistrict().getCode());
        if (district == null) {
            district = districtRepo.save(addressUser.getDistrict());
        }

        Commune commune = communeRepo.findCommuneByCode(addressUser.getCommune().getCode());
        if (commune == null) {
            commune = communeRepo.save(addressUser.getCommune());
        }
        AddressUser addressUserFind = AddressUser.builder()
                .addressUserId(addressUser.getAddressUserId())
                .user(user)
                .homeAddress(addressUser.getHomeAddress())
                .commune(commune)
                .district(district)
                .province(province)
                .phone(addressUser.getPhone())
                .build();

        addressUserRepository.save(addressUserFind);
    }

    @Override
    public void addNewAddress(AddressUserDTO addressUserDTO, Users users) {
        Province province = provinceRepo.findProvinceByCode(addressUserDTO.getProvince().getCode());
        if (province == null) {
            province = provinceRepo.save(addressUserDTO.getProvince());
        }

        District district = districtRepo.findDistrictByCode(addressUserDTO.getDistrict().getCode());
        if (district == null) {
            district = districtRepo.save(addressUserDTO.getDistrict());
        }

        Commune commune = communeRepo.findCommuneByCode(addressUserDTO.getCommune().getCode());
        if (commune == null) {
            commune = communeRepo.save(addressUserDTO.getCommune());
        }

        AddressUser addressUser = AddressUser.builder()
                .user(users)
                .homeAddress(addressUserDTO.getHomeAddress())
                .commune(commune)
                .district(district)
                .province(province)
                .phone(addressUserDTO.getPhone())
                .build();

        addressUserRepository.save(addressUser);
    }



}
