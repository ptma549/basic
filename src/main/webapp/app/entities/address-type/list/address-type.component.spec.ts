import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { AddressTypeService } from '../service/address-type.service';

import { AddressTypeComponent } from './address-type.component';

describe('AddressType Management Component', () => {
  let comp: AddressTypeComponent;
  let fixture: ComponentFixture<AddressTypeComponent>;
  let service: AddressTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [AddressTypeComponent],
    })
      .overrideTemplate(AddressTypeComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AddressTypeComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AddressTypeService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.addressTypes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
