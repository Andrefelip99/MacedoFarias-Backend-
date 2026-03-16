package com.example.confeitariaMacedoFarias.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.confeitariaMacedoFarias.dto.ItemOrderInsertDto;
import com.example.confeitariaMacedoFarias.dto.ItemOrderResponseDto;
import com.example.confeitariaMacedoFarias.entities.ItemOrder;
import com.example.confeitariaMacedoFarias.entities.Order;
import com.example.confeitariaMacedoFarias.entities.Product;
import com.example.confeitariaMacedoFarias.exceptions.DatabaseException;
import com.example.confeitariaMacedoFarias.exceptions.ResourceNotFoundException;
import com.example.confeitariaMacedoFarias.repositories.ItemOrderRepository;
import com.example.confeitariaMacedoFarias.repositories.OrderRepository;
import com.example.confeitariaMacedoFarias.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ItemOrderService {

    private final ItemOrderRepository itemOrderRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @Transactional
    public ItemOrderResponseDto insert(Long orderId, ItemOrderInsertDto dto) {
        Order order = orderRepository.findByIdWithDetails(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido com id " + orderId + " não encontrado"));

        Product product = productRepository.findByIdWithCategories(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto com id " + dto.getProductId() + " não encontrado"));

        ItemOrder item = new ItemOrder();
        item.setOrder(order);
        item.setProduct(product);
        item.setQuantity(dto.getQuantity());
        item.setPriceUnit(product.getPrice());

        item = itemOrderRepository.save(item);

        // Atualizar o total do pedido
        orderService.updateTotal(orderId);

        return new ItemOrderResponseDto(item);
    }

    @Transactional(readOnly = true)
    public List<ItemOrderResponseDto> findByOrderId(Long orderId) {
        List<ItemOrder> items = itemOrderRepository.findByOrderIdWithProduct(orderId);
        return items.stream()
                    .map(ItemOrderResponseDto::new)
                    .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long orderId, Long itemId) {
        try {
            ItemOrder item = itemOrderRepository.findById(itemId)
                    .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado"));
            if (!item.getOrder().getId().equals(orderId)) {
                throw new ResourceNotFoundException("Item não pertence ao pedido informado");
            }
            itemOrderRepository.deleteById(itemId);
            // Atualizar o total do pedido
            orderService.updateTotal(orderId);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Não é possível deletar o item do pedido com id " + itemId);
        }
    }
}

