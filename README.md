THEATRE has ---- screens							</br>
SCREEN has ----- seats							</br>
SEAT has ------- rowNum + seatNum				</br>

MOVIE has ------ id + name									</br>
SHOW has ------- MOVIE + SCREEN + date + duration			</br>

BOOKING has ---- SHOW + list of seats booked + user + status



==================

ADMIN:											</br>
TheatreResource::	/createTheatre				</br>
TheatreResource::	/attachScreenInTheatre		</br>
TheatreResource::	/attachSeatInScreen			</br>
MovieResource::		/createMovie					</br>
ShowResource::		/createShow					</br>


USER:											</br>
ShowResource::		/getAvailableSeats			</br>
BookingResource:	:	/createBooking				</br>
PaymentsResource::	/paymentFailed				</br>
PaymentsResource::	/paymentSuccess				</br>
