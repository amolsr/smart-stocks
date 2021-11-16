import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { SearchResponse } from 'src/app/Interface/SearchResponse';
import { SearchService } from 'src/app/services/search.service';
import { UiService } from 'src/app/services/ui.service';

@Component({
  selector: 'app-discover',
  templateUrl: './discover.component.html',
  styleUrls: ['./discover.component.css']
})
export class DiscoverComponent implements OnInit {

  results: SearchResponse[] = [];
  searchTerm$ = new Subject<string>();

  constructor(private searchService: SearchService, private router: Router, private uiService: UiService) {
    this.searchService.search(this.searchTerm$)
      .subscribe((results: any) => {
        console.log(results)
        this.results = results.data;
      });
  }

  makeSubject(event: any) {
    this.searchTerm$.next(event.target.value);
  }

  ngOnInit(): void {
  }

}
