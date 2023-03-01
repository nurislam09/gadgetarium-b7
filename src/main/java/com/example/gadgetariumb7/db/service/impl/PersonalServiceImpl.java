package com.example.gadgetariumb7.db.service.impl;

import com.example.gadgetariumb7.db.entity.Order;
import com.example.gadgetariumb7.db.entity.Product;
import com.example.gadgetariumb7.db.entity.User;
import com.example.gadgetariumb7.db.repository.OrderRepository;
import com.example.gadgetariumb7.db.repository.ProductRepository;
import com.example.gadgetariumb7.db.repository.UserRepository;
import com.example.gadgetariumb7.db.service.PersonalService;
import com.example.gadgetariumb7.dto.request.ChangePasswordRequest;
import com.example.gadgetariumb7.dto.request.UserUpdateRequest;
import com.example.gadgetariumb7.dto.response.*;
import com.example.gadgetariumb7.exceptions.BadRequestException;
import com.example.gadgetariumb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonalServiceImpl implements PersonalService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final AuthenticationServiceImpl authenticationService;
    private final PasswordEncoder passwordEncoder;

    private User getAuthenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login).orElseThrow(() -> new NotFoundException("User not found!"));
    }

    @Override
    public List<PersonalOrderResponse> getAllPersonalOrders() {
        User user = getAuthenticateUser();
        List<PersonalOrderResponse> orders = orderRepository.getAllPersonalOrders(user.getId());
        if (CollectionUtils.isEmpty(orders))
            throw new NotFoundException("User's order history is empty");
        return orders;
    }

    @Override
    public PersonalOrderByIdResponse getByIdPersonalOrder(Long orderId) {
        User user = getAuthenticateUser();
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException(String.format("Order with id %d not found", orderId)));

        if (!user.getOrders().contains(order))
            throw new NotFoundException(String.format("Order with id %d not found in order history", orderId));

        List<ProductCardResponse> subproductsResponses = new ArrayList<>();
        order.getSubproducts().forEach(s -> {
            Product p = productRepository.findById(s.getProduct().getId()).orElseThrow(() -> new NotFoundException(String.format("Product with id %d not found", s.getProduct().getId())));
            subproductsResponses.add(new ProductCardResponse(s.getId(), p.getProductName(), s.getImages().get(0), p.getProductRating(), p.getUsersReviews().size(), s.getPrice()));
        });

        return new PersonalOrderByIdResponse(order.getOrderNumber(), subproductsResponses, order.getDeliveryStatus(), user.getFirstName() + " " + user.getLastName(), user.getFirstName(), user.getLastName(), order.getAddress(), user.getPhoneNumber(), user.getEmail(), order.getDateOfOrder().toLocalDate(), order.getPayment(), order.getTotalSum() - order.getTotalDiscount(), order.getTotalSum());
    }

    @Override
    public List<ProductCardResponse> getAllPersonalFavorite() {
        User user = getAuthenticateUser();
        if (CollectionUtils.isEmpty(user.getFavoritesList()))
            throw new NotFoundException("User's favorites products is empty");
        return user.getFavoritesList().stream().map(x -> new ProductCardResponse(x.getId(), x.getProductName(), x.getProductImage(), x.getProductRating(), x.getUsersReviews().size(), x.getProductPrice())).toList();
    }

    @Override
    public Object updateUser(UserUpdateRequest request) {
        User user = getAuthenticateUser();

        if (!request.getFirstName().replace(" ", "").equals(user.getFirstName()))
            user.setFirstName(request.getFirstName());

        if (!user.getLastName().equals(request.getLastName().replace(" ", "")))
            user.setLastName(request.getLastName());

        if (!user.getPhoneNumber().equals(request.getPhoneNumber().replace(" ", "")))
            user.setPhoneNumber(request.getPhoneNumber());

        if (request.getAddress() != null && user.getAddress() == null || !user.getAddress().equals(request.getAddress().replace(" ", ""))) {
            user.setAddress(request.getAddress());
        }

        if (request.getImage() != null && user.getImage() == null || !user.getImage().equals(request.getImage())) {
            user.setImage(request.getImage());
        }

        if (!user.getEmail().equals(request.getEmail().replace(" ", ""))) {
            if (userRepository.existsByEmail(request.getEmail()))
                throw new BadRequestException(String.format("User with email %s already exist", request.getEmail()));

            user.setEmail(request.getEmail());

            AuthenticationResponse authenticationResponse = authenticationService.getToken(user);

            userRepository.save(user);
            return authenticationResponse;
        }

        userRepository.save(user);

        return new SimpleResponse("User successfully updated", "ok");
    }

    @Override
    public PersonalUserResponse getPersonalUser() {
        User u = getAuthenticateUser();
        return new PersonalUserResponse(u.getFirstName(), u.getLastName(), u.getEmail(), u.getPhoneNumber(), u.getAddress());
    }

    @Override
    public SimpleResponse clearOrdersHistory() {
        User user = getAuthenticateUser();
        user.getOrders().forEach(x -> x.setUser(null));
        user.getOrders().clear();
        userRepository.save(user);
        return new SimpleResponse("User's order history successfully cleared", "ok");
    }

    @Override
    public SimpleResponse changePassword(ChangePasswordRequest request) {
        User user = getAuthenticateUser();

        boolean matches = passwordEncoder.matches(request.getCurrentPassword(), user.getPassword());

        if (!matches) {
            throw new BadRequestException("invalid current password");
        }

        if (request.getNewPassword().length() < 6){
            throw new BadRequestException("Password should be more than 5");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);
        return new SimpleResponse("Password successfully updated", "ok");
    }
}
