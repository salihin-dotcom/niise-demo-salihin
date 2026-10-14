package my.gov.imi.niisesali_demo;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/passports")
public class PassportController {
    
    private final PassportService service;
    private final ModelMapper mapper;

    // ===================================================================
    // PUBLIC ENDPOINTS (Hides Database IDs)
    // ===================================================================

    /**
     * Public endpoint to view and search passports without exposing backend IDs.
     * Example: GET /api/v1/passports?fullname=Ali&icno=90
     */
    @GetMapping
    public List<PassportDTO> getPassports(
            @RequestParam(value = "fullname", required = false) String fullname,
            @RequestParam(value = "icno", required = false) String icno) {
            
        return service.searchPassports(fullname, icno)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Public endpoint to submit/create a new passport record.
     */
    @PostMapping
    public PassportDTO postPassport(@Valid @RequestBody PassportDTO passportDto) {
        var entity = convertToEntity(passportDto);
        var passport = service.addPassport(entity);

        return convertToDto(passport);
    }

    // ===================================================================
    // ADMINISTRATIVE ENDPOINTS (Exposes and Uses PassportAdminDto with IDs)
    // ===================================================================

    /**
     * Administrative dashboard endpoint to see and search records with their backend IDs.
     * Example: GET /api/v1/passports/admin?icno=8801
     */
    @GetMapping("/admin")
    public List<PassportAdminDTO> getPassportsForAdmin(
            @RequestParam(value = "fullname", required = false) String fullname,
            @RequestParam(value = "icno", required = false) String icno) {
            
        return service.searchPassports(fullname, icno)
                .stream()
                .map(this::convertToAdminDto)
                .collect(Collectors.toList());
    }

    /**
     * Admin function to fetch a specific passport record by its exact ID.
     */
    @GetMapping("/admin/{id}")
    public PassportAdminDTO getPassportByIdForAdmin(@PathVariable("id") int id) {
        return convertToAdminDto(service.findPassportById(id));
    }

    /**
     * Admin function to completely update a passport's fields using the ID specified in the body.
     */
    @PutMapping("/admin")
    public PassportAdminDTO updatePassportAdmin(@Valid @RequestBody PassportAdminDTO adminDto) {
        var entity = convertToEntityFromAdmin(adminDto);
        var updatedEntity = service.updatePassport(adminDto.getId(), entity);
        return convertToAdminDto(updatedEntity);
    }

    /**
     * Admin function to create a new passport record; `icno` is accepted as a plain field name.
     */
    @PostMapping("/admin-no-dash")
    public PassportAdminDTO postPassportAdminNoDash(@Valid @RequestBody PassportAdminDTO adminDto) {
        var icno = adminDto.getIcno();
        var entity = convertToEntityFromAdmin(adminDto);
        if (icno != null) {
            entity.setIcno(icno.replace("-", ""));
        }
        var passport = service.addPassport(entity);
        return convertToAdminDto(passport);
    }

    /**
     * Admin function to permanently delete a passport record from the system.
     */
    @DeleteMapping("/admin/{id}")
    public void deletePassportByIdForAdmin(@PathVariable("id") int id) {
        service.removePassportById(id);
    }

    // ===================================================================
    // PRIVATE MODELMAPPER CONVERSION HELPERS
    // ===================================================================

    private PassportDTO convertToDto(Passport entity) {
        return mapper.map(entity, PassportDTO.class);
    }

    private Passport convertToEntity(PassportDTO dto) {
        return mapper.map(dto, Passport.class);
    }

    private PassportAdminDTO convertToAdminDto(Passport entity) {
        return mapper.map(entity, PassportAdminDTO.class);
    }

    private Passport convertToEntityFromAdmin(PassportAdminDTO adminDto) {
        return mapper.map(adminDto, Passport.class);
    }
}