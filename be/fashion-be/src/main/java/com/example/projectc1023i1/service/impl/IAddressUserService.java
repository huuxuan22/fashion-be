package com.example.projectc1023i1.service.impl;

import com.example.projectc1023i1.Dto.AddressUserDTO;
import com.example.projectc1023i1.model.AddressUser;
import com.example.projectc1023i1.model.Users;

import java.util.List;

public interface IAddressUserService {
    void addAddressUser(AddressUser addressUser);
    List<AddressUser> getAddressUser(Integer userId);
    void deleteAddressUser(Integer id);
    void updateAddress(AddressUser addressUser,Users users);
    void addNewAddress(AddressUserDTO addressUserDTO,Users users);
}
