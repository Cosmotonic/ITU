FUNCTION hentAlleVarianter(product_id):
    SELECT variant_id, farve, størrelse, lager_antal, billede
    FROM VARIANT
    WHERE product_id = product_id

FUNCTION hentVariant(variant_id):
    SELECT variant_id, farve, størrelse, lager_antal, billede
    FROM VARIANT
    WHERE variant_id = variant_id

FUNCTION beregnPris(variant_id, user_id):
    price = SELECT basis_pris
            FROM PRODUCT p
            JOIN VARIANT v ON v.product_id = p.product_id
            WHERE v.variant_id = variant_id

    rabat = SELECT rabat_procent
            FROM RABAT_AFTALE
            WHERE user_id = user_id
            AND product_id = v.product_id
            AND gyldig_til >= idag

    IF rabat findes:
        RETURN price * (1 - rabat)
    ELSE:
        RETURN price

FUNCTION validateKurv(kurv):
    FOR each item IN kurv:
        variant = SELECT lager_antal
                  FROM VARIANT
                  WHERE variant_id = item.variant_id

        IF variant.lager_antal < item.quantity:
            RETURN fejl "varen er udsolgt: " + item.variant_id

    RETURN ok

FUNCTION opretOrder(user_id, adresse):
    INSERT INTO ORDER (user_id, adresse, oprettet, status)
    VALUES (user_id, adresse, NOW(), "PENDING_PAYMENT")
    RETURN order_id

FUNCTION opretOrdrelinjer(order_id, kurv):
    user_id = SELECT user_id FROM ORDER WHERE order_id = order_id

    FOR each item IN kurv:
        variant_id = item.variant_id
        price_at_purchase = beregnPris(variant_id, user_id)

        INSERT INTO ORDRELINJE (order_id, variant_id, quantity, price_at_purchase)
        VALUES (order_id, variant_id, item.quantity, price_at_purchase)

    total = SUM af price_at_purchase * quantity for alle items
    UPDATE ORDER SET total_pris = total WHERE order_id = order_id

FUNCTION behandleBetaling(order_id, betalings_info):
    total_pris = SELECT total_pris FROM ORDER WHERE order_id = order_id

    IF handlePayment(betalings_info, total_pris) == success:
        UPDATE ORDER SET status = "PAID" WHERE order_id = order_id
        RETURN true
    ELSE:
        UPDATE ORDER SET status = "PAYMENT_FAILED" WHERE order_id = order_id
        RETURN false

FUNCTION opdaterLager(kurv):
    FOR each item IN kurv:
        UPDATE VARIANT
        SET lager_antal = lager_antal - item.quantity
        WHERE variant_id = item.variant_id

FUNCTION notifikationer(order_id):
    email = SELECT u.email
            FROM USER u
            JOIN ORDER o ON o.user_id = u.user_id
            WHERE o.order_id = order_id

    sendMail(email, "Din ordre er bekræftet")
    sendMail(packing_team, "Ny ordre klar til pakning: " + order_id)

FUNCTION checkout(user_id, adresse, kurv, betalings_info):

    IF validateKurv(kurv) == fejl:
        RETURN fejl "en eller flere varer er udsolgt"

    order_id = opretOrder(user_id, adresse)

    opretOrdrelinjer(order_id, kurv)

    IF behandleBetaling(order_id, betalings_info) == false:
        RETURN fejl "betaling fejlede"

    opdaterLager(kurv)

    notifikationer(order_id)

    RETURN "ordre gennemført"