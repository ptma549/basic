import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAddressType } from '../address-type.model';
import { AddressTypeService } from '../service/address-type.service';
import { AddressTypeDeleteDialogComponent } from '../delete/address-type-delete-dialog.component';

@Component({
  selector: 'jhi-address-type',
  templateUrl: './address-type.component.html',
})
export class AddressTypeComponent implements OnInit {
  addressTypes?: IAddressType[];
  isLoading = false;

  constructor(protected addressTypeService: AddressTypeService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.addressTypeService.query().subscribe(
      (res: HttpResponse<IAddressType[]>) => {
        this.isLoading = false;
        this.addressTypes = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IAddressType): number {
    return item.id!;
  }

  delete(addressType: IAddressType): void {
    const modalRef = this.modalService.open(AddressTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.addressType = addressType;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
