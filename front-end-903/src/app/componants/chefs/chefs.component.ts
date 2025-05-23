import {Component, OnInit} from '@angular/core';
import {ChefService} from '../../../service/chef.service';
import {Chef} from '../../../model/chef';

@Component({
  selector: 'app-chefs',
  templateUrl: './chefs.component.html',
  styleUrls: ['./chefs.component.css']
})
export class ChefsComponent implements OnInit {
  chefs: Chef[] = [];

  constructor(private chefService: ChefService) {
  }

  ngOnInit(): void {
    this.getAllChefs();
  }

  getAllChefs(): void {
    this.chefService.getAllChefs().subscribe(chefs => this.chefs = chefs);
  }

}
