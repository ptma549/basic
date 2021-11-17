package com.basic.web.rest;

import com.basic.domain.AddressType;
import com.basic.repository.AddressTypeRepository;
import com.basic.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.basic.domain.AddressType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AddressTypeResource {

    private final Logger log = LoggerFactory.getLogger(AddressTypeResource.class);

    private static final String ENTITY_NAME = "addressType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AddressTypeRepository addressTypeRepository;

    public AddressTypeResource(AddressTypeRepository addressTypeRepository) {
        this.addressTypeRepository = addressTypeRepository;
    }

    /**
     * {@code POST  /address-types} : Create a new addressType.
     *
     * @param addressType the addressType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new addressType, or with status {@code 400 (Bad Request)} if the addressType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/address-types")
    public ResponseEntity<AddressType> createAddressType(@RequestBody AddressType addressType) throws URISyntaxException {
        log.debug("REST request to save AddressType : {}", addressType);
        if (addressType.getId() != null) {
            throw new BadRequestAlertException("A new addressType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AddressType result = addressTypeRepository.save(addressType);
        return ResponseEntity
            .created(new URI("/api/address-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /address-types/:id} : Updates an existing addressType.
     *
     * @param id the id of the addressType to save.
     * @param addressType the addressType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated addressType,
     * or with status {@code 400 (Bad Request)} if the addressType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the addressType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/address-types/{id}")
    public ResponseEntity<AddressType> updateAddressType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AddressType addressType
    ) throws URISyntaxException {
        log.debug("REST request to update AddressType : {}, {}", id, addressType);
        if (addressType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, addressType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!addressTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AddressType result = addressTypeRepository.save(addressType);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, addressType.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /address-types/:id} : Partial updates given fields of an existing addressType, field will ignore if it is null
     *
     * @param id the id of the addressType to save.
     * @param addressType the addressType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated addressType,
     * or with status {@code 400 (Bad Request)} if the addressType is not valid,
     * or with status {@code 404 (Not Found)} if the addressType is not found,
     * or with status {@code 500 (Internal Server Error)} if the addressType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/address-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AddressType> partialUpdateAddressType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AddressType addressType
    ) throws URISyntaxException {
        log.debug("REST request to partial update AddressType partially : {}, {}", id, addressType);
        if (addressType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, addressType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!addressTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AddressType> result = addressTypeRepository
            .findById(addressType.getId())
            .map(existingAddressType -> {
                if (addressType.getName() != null) {
                    existingAddressType.setName(addressType.getName());
                }
                if (addressType.getPosition() != null) {
                    existingAddressType.setPosition(addressType.getPosition());
                }

                return existingAddressType;
            })
            .map(addressTypeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, addressType.getId().toString())
        );
    }

    /**
     * {@code GET  /address-types} : get all the addressTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of addressTypes in body.
     */
    @GetMapping("/address-types")
    public List<AddressType> getAllAddressTypes() {
        log.debug("REST request to get all AddressTypes");
        return addressTypeRepository.findAll();
    }

    /**
     * {@code GET  /address-types/:id} : get the "id" addressType.
     *
     * @param id the id of the addressType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the addressType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/address-types/{id}")
    public ResponseEntity<AddressType> getAddressType(@PathVariable Long id) {
        log.debug("REST request to get AddressType : {}", id);
        Optional<AddressType> addressType = addressTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(addressType);
    }

    /**
     * {@code DELETE  /address-types/:id} : delete the "id" addressType.
     *
     * @param id the id of the addressType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/address-types/{id}")
    public ResponseEntity<Void> deleteAddressType(@PathVariable Long id) {
        log.debug("REST request to delete AddressType : {}", id);
        addressTypeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
