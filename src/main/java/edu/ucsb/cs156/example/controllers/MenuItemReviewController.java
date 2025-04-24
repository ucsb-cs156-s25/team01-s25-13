package edu.ucsb.cs156.example.controllers;

import edu.ucsb.cs156.example.entities.MenuItemReview;
import edu.ucsb.cs156.example.entities.UCSBDate;
import edu.ucsb.cs156.example.errors.EntityNotFoundException;
import edu.ucsb.cs156.example.repositories.MenuItemReviewRepository;
import edu.ucsb.cs156.example.repositories.UCSBDateRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import java.time.LocalDateTime;

/**
 * This is a REST controller for MenuItemReviews
 */

@Tag(name = "MenuItemReviews")
@RequestMapping("/api/menuitemreviews")
@RestController
@Slf4j
public class MenuItemReviewController extends ApiController {

    @Autowired
    MenuItemReviewRepository menuItemReviewRepository;

    /**
     * List all menu item reviews
     * 
     * @return an iterable of MenuItemReview
     */
    @Operation(summary= "List all menu item reviews")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/all")
    public Iterable<MenuItemReview> allMenuItemReviews() {
        Iterable<MenuItemReview> reviews = menuItemReviewRepository.findAll();
        return reviews;
    }

    /**
     * Get a single date by id
     * 
     * @param id the id of the date
     * @return a UCSBDate
     */
    // @Operation(summary= "Get a single date")
    // @PreAuthorize("hasRole('ROLE_USER')")
    // @GetMapping("")
    // public UCSBDate getById(
    //         @Parameter(name="id") @RequestParam Long id) {
    //     UCSBDate ucsbDate = ucsbDateRepository.findById(id)
    //             .orElseThrow(() -> new EntityNotFoundException(UCSBDate.class, id));

    //     return ucsbDate;
    // }

    /**
     * Create a new menu item review
     * 
     * @param quarterYYYYQ  the quarter in the format YYYYQ
     * @param name          the name of the date
     * @param localDateTime the date
     * @return the saved ucsbdate
     */
    @Operation(summary= "Create a new menu item review")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/post")
    public MenuItemReview postMenuItemReview(
            @Parameter(name="itemID") @RequestParam int itemID,
            @Parameter(name="reviewerEmail") @RequestParam String reviewerEmail,
            @Parameter(name="stars") @RequestParam int stars,
            @Parameter(name="reviewDate") @RequestParam("reviewDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime reviewDate,
            @Parameter(name="comments") @RequestParam String comments)
            throws JsonProcessingException {

        // For an explanation of @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        // See: https://www.baeldung.com/spring-date-parameters

        log.info("dateReviewed={}", reviewDate);

        MenuItemReview menuItemReview = new MenuItemReview();
        menuItemReview.setItemId(itemID);
        menuItemReview.setReviewerEmail(reviewerEmail);
        menuItemReview.setStars(stars);
        menuItemReview.setDateReviewed(reviewDate);
        menuItemReview.setComments(comments);
        // menuItemReview.setDateReviewed(reviewDate);

        MenuItemReview savedMenuItemReview = menuItemReviewRepository.save(menuItemReview);
        return savedMenuItemReview;
        

    }

    /**
     * Delete a UCSBDate
     * 
     * @param id the id of the date to delete
     * @return a message indicating the date was deleted
     */
    // @Operation(summary= "Delete a UCSBDate")
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    // @DeleteMapping("")
    // public Object deleteUCSBDate(
    //         @Parameter(name="id") @RequestParam Long id) {
    //     UCSBDate ucsbDate = ucsbDateRepository.findById(id)
    //             .orElseThrow(() -> new EntityNotFoundException(UCSBDate.class, id));

    //     ucsbDateRepository.delete(ucsbDate);
    //     return genericMessage("UCSBDate with id %s deleted".formatted(id));
    // }

    /**
     * Update a single date
     * 
     * @param id       id of the date to update
     * @param incoming the new date
     * @return the updated date object
     */
    // @Operation(summary= "Update a single date")
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    // @PutMapping("")
    // public UCSBDate updateUCSBDate(
    //         @Parameter(name="id") @RequestParam Long id,
    //         @RequestBody @Valid UCSBDate incoming) {

    //     UCSBDate ucsbDate = ucsbDateRepository.findById(id)
    //             .orElseThrow(() -> new EntityNotFoundException(UCSBDate.class, id));

    //     ucsbDate.setQuarterYYYYQ(incoming.getQuarterYYYYQ());
    //     ucsbDate.setName(incoming.getName());
    //     ucsbDate.setLocalDateTime(incoming.getLocalDateTime());

    //     ucsbDateRepository.save(ucsbDate);

    //     return ucsbDate;
    // }
}
